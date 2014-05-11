// $ANTLR 3.4 src/main/antlr/Antrl3Srt.g 2014-03-18 21:05:30

package srt;

import cc.explain.server.subtitle.parser.srt.SrtElement;
import cc.explain.server.subtitle.parser.srt.SrtFactory;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class Antrl3SrtParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Arrow", "BLOCK", "BLOCKS", "Dashes", "LINE", "LINES", "LineBreak", "Number", "Other", "Spaces", "TIME_RANGE", "Time", "WORD"
    };

    public static final int EOF=-1;
    public static final int Arrow=4;
    public static final int BLOCK=5;
    public static final int BLOCKS=6;
    public static final int Dashes=7;
    public static final int LINE=8;
    public static final int LINES=9;
    public static final int LineBreak=10;
    public static final int Number=11;
    public static final int Other=12;
    public static final int Spaces=13;
    public static final int TIME_RANGE=14;
    public static final int Time=15;
    public static final int WORD=16;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public Antrl3SrtParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public Antrl3SrtParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return Antrl3SrtParser.tokenNames; }
    public String getGrammarFileName() { return "src/main/antlr/Antrl3Srt.g"; }


      String id;
      String startTime;
      String endTime;
      String text;
      public List<SrtElement> srtElements = new java.util.LinkedList<SrtElement>();
      SrtFactory factory = new SrtFactory();


    public static class parse_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "parse"
    // src/main/antlr/Antrl3Srt.g:38:1: parse : ( LineBreak )* blocks ( LineBreak )* EOF -> blocks ;
    public final Antrl3SrtParser.parse_return parse() throws RecognitionException {
        Antrl3SrtParser.parse_return retval = new Antrl3SrtParser.parse_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LineBreak1=null;
        Token LineBreak3=null;
        Token EOF4=null;
        Antrl3SrtParser.blocks_return blocks2 =null;


        Object LineBreak1_tree=null;
        Object LineBreak3_tree=null;
        Object EOF4_tree=null;
        RewriteRuleTokenStream stream_LineBreak=new RewriteRuleTokenStream(adaptor,"token LineBreak");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_blocks=new RewriteRuleSubtreeStream(adaptor,"rule blocks");
        try {
            // src/main/antlr/Antrl3Srt.g:39:2: ( ( LineBreak )* blocks ( LineBreak )* EOF -> blocks )
            // src/main/antlr/Antrl3Srt.g:39:4: ( LineBreak )* blocks ( LineBreak )* EOF
            {
            // src/main/antlr/Antrl3Srt.g:39:4: ( LineBreak )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==LineBreak) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:39:4: LineBreak
            	    {
            	    LineBreak1=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_parse82);  
            	    stream_LineBreak.add(LineBreak1);


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            pushFollow(FOLLOW_blocks_in_parse85);
            blocks2=blocks();

            state._fsp--;

            stream_blocks.add(blocks2.getTree());

            // src/main/antlr/Antrl3Srt.g:39:22: ( LineBreak )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==LineBreak) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:39:22: LineBreak
            	    {
            	    LineBreak3=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_parse87);  
            	    stream_LineBreak.add(LineBreak3);


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_parse90);  
            stream_EOF.add(EOF4);


            // AST REWRITE
            // elements: blocks
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 40:3: -> blocks
            {
                adaptor.addChild(root_0, stream_blocks.nextTree());

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "parse"


    public static class blocks_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "blocks"
    // src/main/antlr/Antrl3Srt.g:43:1: blocks : block ( LineBreak ( LineBreak )+ block )* -> ^( BLOCKS ( block )+ ) ;
    public final Antrl3SrtParser.blocks_return blocks() throws RecognitionException {
        Antrl3SrtParser.blocks_return retval = new Antrl3SrtParser.blocks_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LineBreak6=null;
        Token LineBreak7=null;
        Antrl3SrtParser.block_return block5 =null;

        Antrl3SrtParser.block_return block8 =null;


        Object LineBreak6_tree=null;
        Object LineBreak7_tree=null;
        RewriteRuleTokenStream stream_LineBreak=new RewriteRuleTokenStream(adaptor,"token LineBreak");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // src/main/antlr/Antrl3Srt.g:44:2: ( block ( LineBreak ( LineBreak )+ block )* -> ^( BLOCKS ( block )+ ) )
            // src/main/antlr/Antrl3Srt.g:44:4: block ( LineBreak ( LineBreak )+ block )*
            {
            pushFollow(FOLLOW_block_in_blocks108);
            block5=block();

            state._fsp--;

            stream_block.add(block5.getTree());

            // src/main/antlr/Antrl3Srt.g:44:10: ( LineBreak ( LineBreak )+ block )*
            loop4:
            do {
                int alt4=2;
                alt4 = dfa4.predict(input);
                switch (alt4) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:44:11: LineBreak ( LineBreak )+ block
            	    {
            	    LineBreak6=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_blocks111);  
            	    stream_LineBreak.add(LineBreak6);


            	    // src/main/antlr/Antrl3Srt.g:44:21: ( LineBreak )+
            	    int cnt3=0;
            	    loop3:
            	    do {
            	        int alt3=2;
            	        int LA3_0 = input.LA(1);

            	        if ( (LA3_0==LineBreak) ) {
            	            alt3=1;
            	        }


            	        switch (alt3) {
            	    	case 1 :
            	    	    // src/main/antlr/Antrl3Srt.g:44:21: LineBreak
            	    	    {
            	    	    LineBreak7=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_blocks113);  
            	    	    stream_LineBreak.add(LineBreak7);


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt3 >= 1 ) break loop3;
            	                EarlyExitException eee =
            	                    new EarlyExitException(3, input);
            	                throw eee;
            	        }
            	        cnt3++;
            	    } while (true);


            	    pushFollow(FOLLOW_block_in_blocks116);
            	    block8=block();

            	    state._fsp--;

            	    stream_block.add(block8.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            // AST REWRITE
            // elements: block
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 44:40: -> ^( BLOCKS ( block )+ )
            {
                // src/main/antlr/Antrl3Srt.g:44:43: ^( BLOCKS ( block )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(BLOCKS, "BLOCKS")
                , root_1);

                if ( !(stream_block.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_block.hasNext() ) {
                    adaptor.addChild(root_1, stream_block.nextTree());

                }
                stream_block.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "blocks"


    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "block"
    // src/main/antlr/Antrl3Srt.g:47:1: block : Number ( Spaces )? LineBreak time_range LineBreak text_lines -> ^( BLOCK Number time_range text_lines ) ;
    public final Antrl3SrtParser.block_return block() throws RecognitionException {
        Antrl3SrtParser.block_return retval = new Antrl3SrtParser.block_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token Number9=null;
        Token Spaces10=null;
        Token LineBreak11=null;
        Token LineBreak13=null;
        Antrl3SrtParser.time_range_return time_range12 =null;

        Antrl3SrtParser.text_lines_return text_lines14 =null;


        Object Number9_tree=null;
        Object Spaces10_tree=null;
        Object LineBreak11_tree=null;
        Object LineBreak13_tree=null;
        RewriteRuleTokenStream stream_LineBreak=new RewriteRuleTokenStream(adaptor,"token LineBreak");
        RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");
        RewriteRuleTokenStream stream_Spaces=new RewriteRuleTokenStream(adaptor,"token Spaces");
        RewriteRuleSubtreeStream stream_time_range=new RewriteRuleSubtreeStream(adaptor,"rule time_range");
        RewriteRuleSubtreeStream stream_text_lines=new RewriteRuleSubtreeStream(adaptor,"rule text_lines");
        try {
            // src/main/antlr/Antrl3Srt.g:48:2: ( Number ( Spaces )? LineBreak time_range LineBreak text_lines -> ^( BLOCK Number time_range text_lines ) )
            // src/main/antlr/Antrl3Srt.g:48:4: Number ( Spaces )? LineBreak time_range LineBreak text_lines
            {
            Number9=(Token)match(input,Number,FOLLOW_Number_in_block139);  
            stream_Number.add(Number9);


            // src/main/antlr/Antrl3Srt.g:48:11: ( Spaces )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==Spaces) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:48:11: Spaces
                    {
                    Spaces10=(Token)match(input,Spaces,FOLLOW_Spaces_in_block141);  
                    stream_Spaces.add(Spaces10);


                    }
                    break;

            }


            LineBreak11=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_block144);  
            stream_LineBreak.add(LineBreak11);


            pushFollow(FOLLOW_time_range_in_block146);
            time_range12=time_range();

            state._fsp--;

            stream_time_range.add(time_range12.getTree());

            LineBreak13=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_block148);  
            stream_LineBreak.add(LineBreak13);


            pushFollow(FOLLOW_text_lines_in_block150);
            text_lines14=text_lines();

            state._fsp--;

            stream_text_lines.add(text_lines14.getTree());


             		id = (Number9!=null?Number9.getText():null);
             		text=(text_lines14!=null?input.toString(text_lines14.start,text_lines14.stop):null);
             		SrtElement element = factory.createSrtElement(id, startTime, endTime, text);
             		srtElements.add(element);
             		
             		//System.err.println("id= "+id);
             		//System.out.println("startTime= "+startTime);
             		//System.out.println("endTime= "+endTime);
             		//System.out.println("txt= "+text);
             	

            // AST REWRITE
            // elements: text_lines, Number, time_range
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 60:3: -> ^( BLOCK Number time_range text_lines )
            {
                // src/main/antlr/Antrl3Srt.g:60:6: ^( BLOCK Number time_range text_lines )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(BLOCK, "BLOCK")
                , root_1);

                adaptor.addChild(root_1, 
                stream_Number.nextNode()
                );

                adaptor.addChild(root_1, stream_time_range.nextTree());

                adaptor.addChild(root_1, stream_text_lines.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "block"


    public static class time_range_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "time_range"
    // src/main/antlr/Antrl3Srt.g:64:1: time_range : t1= Time ( Spaces )? Arrow ( Spaces )? t2= Time ( Spaces )? -> ^( TIME_RANGE Time Time ) ;
    public final Antrl3SrtParser.time_range_return time_range() throws RecognitionException {
        Antrl3SrtParser.time_range_return retval = new Antrl3SrtParser.time_range_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token t1=null;
        Token t2=null;
        Token Spaces15=null;
        Token Arrow16=null;
        Token Spaces17=null;
        Token Spaces18=null;

        Object t1_tree=null;
        Object t2_tree=null;
        Object Spaces15_tree=null;
        Object Arrow16_tree=null;
        Object Spaces17_tree=null;
        Object Spaces18_tree=null;
        RewriteRuleTokenStream stream_Time=new RewriteRuleTokenStream(adaptor,"token Time");
        RewriteRuleTokenStream stream_Arrow=new RewriteRuleTokenStream(adaptor,"token Arrow");
        RewriteRuleTokenStream stream_Spaces=new RewriteRuleTokenStream(adaptor,"token Spaces");

        try {
            // src/main/antlr/Antrl3Srt.g:65:2: (t1= Time ( Spaces )? Arrow ( Spaces )? t2= Time ( Spaces )? -> ^( TIME_RANGE Time Time ) )
            // src/main/antlr/Antrl3Srt.g:65:4: t1= Time ( Spaces )? Arrow ( Spaces )? t2= Time ( Spaces )?
            {
            t1=(Token)match(input,Time,FOLLOW_Time_in_time_range186);  
            stream_Time.add(t1);


            // src/main/antlr/Antrl3Srt.g:65:12: ( Spaces )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==Spaces) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:65:12: Spaces
                    {
                    Spaces15=(Token)match(input,Spaces,FOLLOW_Spaces_in_time_range188);  
                    stream_Spaces.add(Spaces15);


                    }
                    break;

            }


            Arrow16=(Token)match(input,Arrow,FOLLOW_Arrow_in_time_range191);  
            stream_Arrow.add(Arrow16);


            // src/main/antlr/Antrl3Srt.g:65:26: ( Spaces )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==Spaces) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:65:26: Spaces
                    {
                    Spaces17=(Token)match(input,Spaces,FOLLOW_Spaces_in_time_range193);  
                    stream_Spaces.add(Spaces17);


                    }
                    break;

            }


            t2=(Token)match(input,Time,FOLLOW_Time_in_time_range198);  
            stream_Time.add(t2);


            // src/main/antlr/Antrl3Srt.g:65:42: ( Spaces )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==Spaces) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:65:42: Spaces
                    {
                    Spaces18=(Token)match(input,Spaces,FOLLOW_Spaces_in_time_range200);  
                    stream_Spaces.add(Spaces18);


                    }
                    break;

            }


             
             		startTime=(t1!=null?t1.getText():null);
             		endTime = (t2!=null?t2.getText():null);
             	

            // AST REWRITE
            // elements: Time, Time
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 70:3: -> ^( TIME_RANGE Time Time )
            {
                // src/main/antlr/Antrl3Srt.g:70:6: ^( TIME_RANGE Time Time )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(TIME_RANGE, "TIME_RANGE")
                , root_1);

                adaptor.addChild(root_1, 
                stream_Time.nextNode()
                );

                adaptor.addChild(root_1, 
                stream_Time.nextNode()
                );

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "time_range"


    public static class text_lines_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "text_lines"
    // src/main/antlr/Antrl3Srt.g:73:1: text_lines : line ( LineBreak line )* -> ^( LINES ( line )+ ) ;
    public final Antrl3SrtParser.text_lines_return text_lines() throws RecognitionException {
        Antrl3SrtParser.text_lines_return retval = new Antrl3SrtParser.text_lines_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LineBreak20=null;
        Antrl3SrtParser.line_return line19 =null;

        Antrl3SrtParser.line_return line21 =null;


        Object LineBreak20_tree=null;
        RewriteRuleTokenStream stream_LineBreak=new RewriteRuleTokenStream(adaptor,"token LineBreak");
        RewriteRuleSubtreeStream stream_line=new RewriteRuleSubtreeStream(adaptor,"rule line");
        try {
            // src/main/antlr/Antrl3Srt.g:74:2: ( line ( LineBreak line )* -> ^( LINES ( line )+ ) )
            // src/main/antlr/Antrl3Srt.g:74:4: line ( LineBreak line )*
            {
            pushFollow(FOLLOW_line_in_text_lines229);
            line19=line();

            state._fsp--;

            stream_line.add(line19.getTree());

            // src/main/antlr/Antrl3Srt.g:74:9: ( LineBreak line )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==LineBreak) ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1==Arrow||LA9_1==Dashes||(LA9_1 >= Number && LA9_1 <= Spaces)) ) {
                        alt9=1;
                    }


                }


                switch (alt9) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:74:10: LineBreak line
            	    {
            	    LineBreak20=(Token)match(input,LineBreak,FOLLOW_LineBreak_in_text_lines232);  
            	    stream_LineBreak.add(LineBreak20);


            	    pushFollow(FOLLOW_line_in_text_lines234);
            	    line21=line();

            	    state._fsp--;

            	    stream_line.add(line21.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            // AST REWRITE
            // elements: line
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 74:27: -> ^( LINES ( line )+ )
            {
                // src/main/antlr/Antrl3Srt.g:74:30: ^( LINES ( line )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LINES, "LINES")
                , root_1);

                if ( !(stream_line.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_line.hasNext() ) {
                    adaptor.addChild(root_1, stream_line.nextTree());

                }
                stream_line.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "text_lines"


    public static class line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "line"
    // src/main/antlr/Antrl3Srt.g:77:1: line : ( Spaces )? word ( Spaces word )* ( Spaces )? -> ^( LINE ( word )+ ) ;
    public final Antrl3SrtParser.line_return line() throws RecognitionException {
        Antrl3SrtParser.line_return retval = new Antrl3SrtParser.line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token Spaces22=null;
        Token Spaces24=null;
        Token Spaces26=null;
        Antrl3SrtParser.word_return word23 =null;

        Antrl3SrtParser.word_return word25 =null;


        Object Spaces22_tree=null;
        Object Spaces24_tree=null;
        Object Spaces26_tree=null;
        RewriteRuleTokenStream stream_Spaces=new RewriteRuleTokenStream(adaptor,"token Spaces");
        RewriteRuleSubtreeStream stream_word=new RewriteRuleSubtreeStream(adaptor,"rule word");
        try {
            // src/main/antlr/Antrl3Srt.g:78:2: ( ( Spaces )? word ( Spaces word )* ( Spaces )? -> ^( LINE ( word )+ ) )
            // src/main/antlr/Antrl3Srt.g:78:4: ( Spaces )? word ( Spaces word )* ( Spaces )?
            {
            // src/main/antlr/Antrl3Srt.g:78:4: ( Spaces )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==Spaces) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:78:4: Spaces
                    {
                    Spaces22=(Token)match(input,Spaces,FOLLOW_Spaces_in_line256);  
                    stream_Spaces.add(Spaces22);


                    }
                    break;

            }


            pushFollow(FOLLOW_word_in_line259);
            word23=word();

            state._fsp--;

            stream_word.add(word23.getTree());

            // src/main/antlr/Antrl3Srt.g:78:17: ( Spaces word )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==Spaces) ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1==Arrow||LA11_1==Dashes||(LA11_1 >= Number && LA11_1 <= Other)) ) {
                        alt11=1;
                    }


                }


                switch (alt11) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:78:18: Spaces word
            	    {
            	    Spaces24=(Token)match(input,Spaces,FOLLOW_Spaces_in_line262);  
            	    stream_Spaces.add(Spaces24);


            	    pushFollow(FOLLOW_word_in_line264);
            	    word25=word();

            	    state._fsp--;

            	    stream_word.add(word25.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            // src/main/antlr/Antrl3Srt.g:78:32: ( Spaces )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==Spaces) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:78:32: Spaces
                    {
                    Spaces26=(Token)match(input,Spaces,FOLLOW_Spaces_in_line268);  
                    stream_Spaces.add(Spaces26);


                    }
                    break;

            }


            // AST REWRITE
            // elements: word
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 78:40: -> ^( LINE ( word )+ )
            {
                // src/main/antlr/Antrl3Srt.g:78:43: ^( LINE ( word )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(LINE, "LINE")
                , root_1);

                if ( !(stream_word.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_word.hasNext() ) {
                    adaptor.addChild(root_1, stream_word.nextTree());

                }
                stream_word.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "line"


    public static class word_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "word"
    // src/main/antlr/Antrl3Srt.g:81:1: word : ( Other | Number | Dashes | Arrow )+ -> WORD[$text] ;
    public final Antrl3SrtParser.word_return word() throws RecognitionException {
        Antrl3SrtParser.word_return retval = new Antrl3SrtParser.word_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token Other27=null;
        Token Number28=null;
        Token Dashes29=null;
        Token Arrow30=null;

        Object Other27_tree=null;
        Object Number28_tree=null;
        Object Dashes29_tree=null;
        Object Arrow30_tree=null;
        RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");
        RewriteRuleTokenStream stream_Dashes=new RewriteRuleTokenStream(adaptor,"token Dashes");
        RewriteRuleTokenStream stream_Arrow=new RewriteRuleTokenStream(adaptor,"token Arrow");
        RewriteRuleTokenStream stream_Other=new RewriteRuleTokenStream(adaptor,"token Other");

        try {
            // src/main/antlr/Antrl3Srt.g:82:2: ( ( Other | Number | Dashes | Arrow )+ -> WORD[$text] )
            // src/main/antlr/Antrl3Srt.g:82:4: ( Other | Number | Dashes | Arrow )+
            {
            // src/main/antlr/Antrl3Srt.g:82:4: ( Other | Number | Dashes | Arrow )+
            int cnt13=0;
            loop13:
            do {
                int alt13=5;
                switch ( input.LA(1) ) {
                case Other:
                    {
                    alt13=1;
                    }
                    break;
                case Number:
                    {
                    alt13=2;
                    }
                    break;
                case Dashes:
                    {
                    alt13=3;
                    }
                    break;
                case Arrow:
                    {
                    alt13=4;
                    }
                    break;

                }

                switch (alt13) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:82:5: Other
            	    {
            	    Other27=(Token)match(input,Other,FOLLOW_Other_in_word290);  
            	    stream_Other.add(Other27);


            	    }
            	    break;
            	case 2 :
            	    // src/main/antlr/Antrl3Srt.g:82:13: Number
            	    {
            	    Number28=(Token)match(input,Number,FOLLOW_Number_in_word294);  
            	    stream_Number.add(Number28);


            	    }
            	    break;
            	case 3 :
            	    // src/main/antlr/Antrl3Srt.g:82:22: Dashes
            	    {
            	    Dashes29=(Token)match(input,Dashes,FOLLOW_Dashes_in_word298);  
            	    stream_Dashes.add(Dashes29);


            	    }
            	    break;
            	case 4 :
            	    // src/main/antlr/Antrl3Srt.g:82:31: Arrow
            	    {
            	    Arrow30=(Token)match(input,Arrow,FOLLOW_Arrow_in_word302);  
            	    stream_Arrow.add(Arrow30);


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 82:39: -> WORD[$text]
            {
                adaptor.addChild(root_0, 
                (Object)adaptor.create(WORD, input.toString(retval.start,input.LT(-1)))
                );

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "word"

    // Delegated rules


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\5\uffff";
    static final String DFA4_eofS =
        "\2\2\1\uffff\1\2\1\uffff";
    static final String DFA4_minS =
        "\2\12\1\uffff\1\12\1\uffff";
    static final String DFA4_maxS =
        "\2\12\1\uffff\1\13\1\uffff";
    static final String DFA4_acceptS =
        "\2\uffff\1\2\1\uffff\1\1";
    static final String DFA4_specialS =
        "\5\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1",
            "\1\3",
            "",
            "\1\3\1\4",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "()* loopback of 44:10: ( LineBreak ( LineBreak )+ block )*";
        }
    }
 

    public static final BitSet FOLLOW_LineBreak_in_parse82 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_blocks_in_parse85 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LineBreak_in_parse87 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EOF_in_parse90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_blocks108 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_LineBreak_in_blocks111 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LineBreak_in_blocks113 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_block_in_blocks116 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_Number_in_block139 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_Spaces_in_block141 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LineBreak_in_block144 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_time_range_in_block146 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LineBreak_in_block148 = new BitSet(new long[]{0x0000000000003890L});
    public static final BitSet FOLLOW_text_lines_in_block150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Time_in_time_range186 = new BitSet(new long[]{0x0000000000002010L});
    public static final BitSet FOLLOW_Spaces_in_time_range188 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Arrow_in_time_range191 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_Spaces_in_time_range193 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_Time_in_time_range198 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_Spaces_in_time_range200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_line_in_text_lines229 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_LineBreak_in_text_lines232 = new BitSet(new long[]{0x0000000000003890L});
    public static final BitSet FOLLOW_line_in_text_lines234 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_Spaces_in_line256 = new BitSet(new long[]{0x0000000000001890L});
    public static final BitSet FOLLOW_word_in_line259 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_Spaces_in_line262 = new BitSet(new long[]{0x0000000000001890L});
    public static final BitSet FOLLOW_word_in_line264 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_Spaces_in_line268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Other_in_word290 = new BitSet(new long[]{0x0000000000001892L});
    public static final BitSet FOLLOW_Number_in_word294 = new BitSet(new long[]{0x0000000000001892L});
    public static final BitSet FOLLOW_Dashes_in_word298 = new BitSet(new long[]{0x0000000000001892L});
    public static final BitSet FOLLOW_Arrow_in_word302 = new BitSet(new long[]{0x0000000000001892L});

}