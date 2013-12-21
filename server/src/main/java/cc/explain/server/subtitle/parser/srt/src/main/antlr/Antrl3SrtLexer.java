// $ANTLR 3.4 src/main/antlr/Antrl3Srt.g 2013-12-14 18:43:26

	package srt;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class Antrl3SrtLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public Antrl3SrtLexer() {} 
    public Antrl3SrtLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public Antrl3SrtLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "src/main/antlr/Antrl3Srt.g"; }

    // $ANTLR start "Time"
    public final void mTime() throws RecognitionException {
        try {
            int _type = Time;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken last=null;

            // src/main/antlr/Antrl3Srt.g:86:2: ( Number ':' ( Number ( ':' ( Number ( ',' (last= Number )? )? )? )? )? )
            // src/main/antlr/Antrl3Srt.g:86:4: Number ':' ( Number ( ':' ( Number ( ',' (last= Number )? )? )? )? )?
            {
            mNumber(); 


            match(':'); 

            // src/main/antlr/Antrl3Srt.g:86:15: ( Number ( ':' ( Number ( ',' (last= Number )? )? )? )? )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:86:16: Number ( ':' ( Number ( ',' (last= Number )? )? )? )?
                    {
                    mNumber(); 


                    // src/main/antlr/Antrl3Srt.g:86:23: ( ':' ( Number ( ',' (last= Number )? )? )? )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==':') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // src/main/antlr/Antrl3Srt.g:86:24: ':' ( Number ( ',' (last= Number )? )? )?
                            {
                            match(':'); 

                            // src/main/antlr/Antrl3Srt.g:86:28: ( Number ( ',' (last= Number )? )? )?
                            int alt3=2;
                            int LA3_0 = input.LA(1);

                            if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                                alt3=1;
                            }
                            switch (alt3) {
                                case 1 :
                                    // src/main/antlr/Antrl3Srt.g:86:29: Number ( ',' (last= Number )? )?
                                    {
                                    mNumber(); 


                                    // src/main/antlr/Antrl3Srt.g:86:36: ( ',' (last= Number )? )?
                                    int alt2=2;
                                    int LA2_0 = input.LA(1);

                                    if ( (LA2_0==',') ) {
                                        alt2=1;
                                    }
                                    switch (alt2) {
                                        case 1 :
                                            // src/main/antlr/Antrl3Srt.g:86:37: ',' (last= Number )?
                                            {
                                            match(','); 

                                            // src/main/antlr/Antrl3Srt.g:86:45: (last= Number )?
                                            int alt1=2;
                                            int LA1_0 = input.LA(1);

                                            if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
                                                alt1=1;
                                            }
                                            switch (alt1) {
                                                case 1 :
                                                    // src/main/antlr/Antrl3Srt.g:86:45: last= Number
                                                    {
                                                    int lastStart43 = getCharIndex();
                                                    int lastStartLine43 = getLine();
                                                    int lastStartCharPos43 = getCharPositionInLine();
                                                    mNumber(); 
                                                    last = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, lastStart43, getCharIndex()-1);
                                                    last.setLine(lastStartLine43);
                                                    last.setCharPositionInLine(lastStartCharPos43);


                                                    }
                                                    break;

                                            }


                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;

            }



                 if((last!=null?last.getText():null) == null) _type = Other;
               

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Time"

    // $ANTLR start "Arrow"
    public final void mArrow() throws RecognitionException {
        try {
            int _type = Arrow;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr/Antrl3Srt.g:91:11: ( '-->' )
            // src/main/antlr/Antrl3Srt.g:91:13: '-->'
            {
            match("-->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Arrow"

    // $ANTLR start "Dashes"
    public final void mDashes() throws RecognitionException {
        try {
            int _type = Dashes;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr/Antrl3Srt.g:92:11: ( ( '-' )+ )
            // src/main/antlr/Antrl3Srt.g:92:13: ( '-' )+
            {
            // src/main/antlr/Antrl3Srt.g:92:13: ( '-' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='-') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:92:13: '-'
            	    {
            	    match('-'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Dashes"

    // $ANTLR start "Number"
    public final void mNumber() throws RecognitionException {
        try {
            int _type = Number;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr/Antrl3Srt.g:93:11: ( ( '0' .. '9' )+ )
            // src/main/antlr/Antrl3Srt.g:93:13: ( '0' .. '9' )+
            {
            // src/main/antlr/Antrl3Srt.g:93:13: ( '0' .. '9' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0 >= '0' && LA7_0 <= '9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Number"

    // $ANTLR start "LineBreak"
    public final void mLineBreak() throws RecognitionException {
        try {
            int _type = LineBreak;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr/Antrl3Srt.g:94:11: ( ( '\\r' )? '\\n' | '\\r' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\r') ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1=='\n') ) {
                    alt9=1;
                }
                else {
                    alt9=2;
                }
            }
            else if ( (LA9_0=='\n') ) {
                alt9=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // src/main/antlr/Antrl3Srt.g:94:13: ( '\\r' )? '\\n'
                    {
                    // src/main/antlr/Antrl3Srt.g:94:13: ( '\\r' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\r') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // src/main/antlr/Antrl3Srt.g:94:13: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    }
                    break;
                case 2 :
                    // src/main/antlr/Antrl3Srt.g:94:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LineBreak"

    // $ANTLR start "Spaces"
    public final void mSpaces() throws RecognitionException {
        try {
            int _type = Spaces;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr/Antrl3Srt.g:95:11: ( ( ' ' | '\\t' )+ )
            // src/main/antlr/Antrl3Srt.g:95:13: ( ' ' | '\\t' )+
            {
            // src/main/antlr/Antrl3Srt.g:95:13: ( ' ' | '\\t' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='\t'||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // src/main/antlr/Antrl3Srt.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Spaces"

    // $ANTLR start "Other"
    public final void mOther() throws RecognitionException {
        try {
            int _type = Other;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/main/antlr/Antrl3Srt.g:96:11: ( . )
            // src/main/antlr/Antrl3Srt.g:96:13: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Other"

    public void mTokens() throws RecognitionException {
        // src/main/antlr/Antrl3Srt.g:1:8: ( Time | Arrow | Dashes | Number | LineBreak | Spaces | Other )
        int alt11=7;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // src/main/antlr/Antrl3Srt.g:1:10: Time
                {
                mTime(); 


                }
                break;
            case 2 :
                // src/main/antlr/Antrl3Srt.g:1:15: Arrow
                {
                mArrow(); 


                }
                break;
            case 3 :
                // src/main/antlr/Antrl3Srt.g:1:21: Dashes
                {
                mDashes(); 


                }
                break;
            case 4 :
                // src/main/antlr/Antrl3Srt.g:1:28: Number
                {
                mNumber(); 


                }
                break;
            case 5 :
                // src/main/antlr/Antrl3Srt.g:1:35: LineBreak
                {
                mLineBreak(); 


                }
                break;
            case 6 :
                // src/main/antlr/Antrl3Srt.g:1:45: Spaces
                {
                mSpaces(); 


                }
                break;
            case 7 :
                // src/main/antlr/Antrl3Srt.g:1:52: Other
                {
                mOther(); 


                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\1\uffff\1\7\1\13\6\uffff\1\7\1\13\4\uffff";
    static final String DFA11_eofS =
        "\17\uffff";
    static final String DFA11_minS =
        "\1\0\1\60\1\55\6\uffff\1\60\1\76\4\uffff";
    static final String DFA11_maxS =
        "\1\uffff\1\72\1\55\6\uffff\1\72\1\76\4\uffff";
    static final String DFA11_acceptS =
        "\3\uffff\2\5\1\6\1\7\1\4\1\1\2\uffff\1\3\1\5\1\6\1\2";
    static final String DFA11_specialS =
        "\1\0\16\uffff}>";
    static final String[] DFA11_transitionS = {
            "\11\6\1\5\1\4\2\6\1\3\22\6\1\5\14\6\1\2\2\6\12\1\uffc6\6",
            "\12\11\1\10",
            "\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\11\1\10",
            "\1\16",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( Time | Arrow | Dashes | Number | LineBreak | Spaces | Other );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_0 = input.LA(1);

                        s = -1;
                        if ( ((LA11_0 >= '0' && LA11_0 <= '9')) ) {s = 1;}

                        else if ( (LA11_0=='-') ) {s = 2;}

                        else if ( (LA11_0=='\r') ) {s = 3;}

                        else if ( (LA11_0=='\n') ) {s = 4;}

                        else if ( (LA11_0=='\t'||LA11_0==' ') ) {s = 5;}

                        else if ( ((LA11_0 >= '\u0000' && LA11_0 <= '\b')||(LA11_0 >= '\u000B' && LA11_0 <= '\f')||(LA11_0 >= '\u000E' && LA11_0 <= '\u001F')||(LA11_0 >= '!' && LA11_0 <= ',')||(LA11_0 >= '.' && LA11_0 <= '/')||(LA11_0 >= ':' && LA11_0 <= '\uFFFF')) ) {s = 6;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }

    }
 

}