grammar Antrl3Srt;


options {
  output=AST;
}


tokens {
  BLOCKS;
  BLOCK;
  TIME_RANGE;
  LINES;
  LINE;
  WORD;
}

@header {
package srt;

import cc.explain.server.subtitle.parser.srt.SrtElement;
import cc.explain.server.subtitle.parser.srt.SrtFactory;
}

@lexer::header {
	package srt;
}

@members {
  String id;
  String startTime;
  String endTime;
  String text;
  public List<SrtElement> srtElements = new java.util.LinkedList<SrtElement>();
  SrtFactory factory = new SrtFactory();
}

parse 
 : LineBreak* blocks LineBreak* EOF 
 	-> blocks
 ;

blocks
 : block (LineBreak LineBreak+ block)* -> ^(BLOCKS block+)
 ;

block 
 : Number Spaces? LineBreak time_range LineBreak text_lines  
 	{
 		id = $Number.text;
 		text=$text_lines.text;
 		SrtElement element = factory.createSrtElement(id, startTime, endTime, text);
 		srtElements.add(element);
 		
 		//System.err.println("id= "+id);
 		//System.out.println("startTime= "+startTime);
 		//System.out.println("endTime= "+endTime);
 		//System.out.println("txt= "+text);
 	} 
 	-> ^(BLOCK Number time_range text_lines)
 
 ;

time_range
 : t1=Time Spaces? Arrow Spaces? t2=Time Spaces? 
 	{ 
 		startTime=$t1.text;
 		endTime = $t2.text;
 	}
 	-> ^(TIME_RANGE Time Time)
 ;

text_lines
 : line (LineBreak line)* -> ^(LINES line+)
 ;

line
 : Spaces? word (Spaces word)* Spaces? -> ^(LINE word+)
 ;

word
 : (Other | Number | Dashes | Arrow)+ -> WORD[$text]
 ;

Time      
 : Number ':' (Number (':' (Number (',' last=Number?)?)?)?)?
   {
     if($last.text == null) $type = Other;
   }
 ;
Arrow     : '-->';
Dashes    : '-'+;
Number    : '0'..'9'+;
LineBreak : '\r'? '\n' | '\r';
Spaces    : (' ' | '\t')+;
Other     : . ;