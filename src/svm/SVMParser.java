// Generated from /home/gabri/Desktop/clp_project/src/svm/SVM.g4 by ANTLR 4.13.1
package svm;

import java.util.HashMap;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SVMParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, LOAD=4, STORE=5, STOREI=6, MOVE=7, ADD=8, ADDI=9, 
		SUB=10, SUBI=11, MUL=12, MULI=13, DIV=14, DIVI=15, PUSH=16, PUSHR=17, 
		POP=18, POPR=19, BRANCH=20, BRANCHEQ=21, BRANCHLESSEQ=22, JUMPSUB=23, 
		RETURNSUB=24, HALT=25, REG=26, LABEL=27, NUMBER=28, WHITESP=29, LINECOMENTS=30, 
		ERR=31;
	public static final int
		RULE_assembly = 0, RULE_instruction = 1;
	private static String[] makeRuleNames() {
		return new String[] {
			"assembly", "instruction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "':'", "'load'", "'store'", "'storei'", "'move'", 
			"'add'", "'addi'", "'sub'", "'subi'", "'mul'", "'muli'", "'div'", "'divi'", 
			"'push'", "'pushr'", "'pop'", "'popr'", "'b'", "'beq'", "'bleq'", "'jsub'", 
			"'rsub'", "'halt'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "LOAD", "STORE", "STOREI", "MOVE", "ADD", "ADDI", 
			"SUB", "SUBI", "MUL", "MULI", "DIV", "DIVI", "PUSH", "PUSHR", "POP", 
			"POPR", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "JUMPSUB", "RETURNSUB", 
			"HALT", "REG", "LABEL", "NUMBER", "WHITESP", "LINECOMENTS", "ERR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssemblyContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public AssemblyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assembly; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAssembly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAssembly(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAssembly(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblyContext assembly() throws RecognitionException {
		AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assembly);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 201326576L) != 0)) {
				{
				{
				setState(4);
				instruction();
				}
				}
				setState(9);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstructionContext extends ParserRuleContext {
		public Token n;
		public Token l;
		public TerminalNode LOAD() { return getToken(SVMParser.LOAD, 0); }
		public List<TerminalNode> REG() { return getTokens(SVMParser.REG); }
		public TerminalNode REG(int i) {
			return getToken(SVMParser.REG, i);
		}
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public TerminalNode STORE() { return getToken(SVMParser.STORE, 0); }
		public TerminalNode STOREI() { return getToken(SVMParser.STOREI, 0); }
		public TerminalNode MOVE() { return getToken(SVMParser.MOVE, 0); }
		public TerminalNode ADD() { return getToken(SVMParser.ADD, 0); }
		public TerminalNode ADDI() { return getToken(SVMParser.ADDI, 0); }
		public TerminalNode SUB() { return getToken(SVMParser.SUB, 0); }
		public TerminalNode SUBI() { return getToken(SVMParser.SUBI, 0); }
		public TerminalNode MUL() { return getToken(SVMParser.MUL, 0); }
		public TerminalNode MULI() { return getToken(SVMParser.MULI, 0); }
		public TerminalNode DIV() { return getToken(SVMParser.DIV, 0); }
		public TerminalNode DIVI() { return getToken(SVMParser.DIVI, 0); }
		public TerminalNode PUSH() { return getToken(SVMParser.PUSH, 0); }
		public TerminalNode PUSHR() { return getToken(SVMParser.PUSHR, 0); }
		public TerminalNode POP() { return getToken(SVMParser.POP, 0); }
		public TerminalNode POPR() { return getToken(SVMParser.POPR, 0); }
		public TerminalNode BRANCH() { return getToken(SVMParser.BRANCH, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public TerminalNode BRANCHEQ() { return getToken(SVMParser.BRANCHEQ, 0); }
		public TerminalNode BRANCHLESSEQ() { return getToken(SVMParser.BRANCHLESSEQ, 0); }
		public TerminalNode JUMPSUB() { return getToken(SVMParser.JUMPSUB, 0); }
		public TerminalNode RETURNSUB() { return getToken(SVMParser.RETURNSUB, 0); }
		public TerminalNode HALT() { return getToken(SVMParser.HALT, 0); }
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LOAD:
				{
				setState(10);
				match(LOAD);
				setState(11);
				match(REG);
				setState(12);
				match(NUMBER);
				setState(13);
				match(T__0);
				setState(14);
				match(REG);
				setState(15);
				match(T__1);
				}
				break;
			case STORE:
				{
				setState(16);
				match(STORE);
				setState(17);
				match(REG);
				setState(18);
				match(NUMBER);
				setState(19);
				match(T__0);
				setState(20);
				match(REG);
				setState(21);
				match(T__1);
				}
				break;
			case STOREI:
				{
				setState(22);
				match(STOREI);
				setState(23);
				match(REG);
				setState(24);
				match(NUMBER);
				}
				break;
			case MOVE:
				{
				setState(25);
				match(MOVE);
				setState(26);
				match(REG);
				setState(27);
				match(REG);
				}
				break;
			case ADD:
				{
				setState(28);
				match(ADD);
				setState(29);
				match(REG);
				setState(30);
				match(REG);
				}
				break;
			case ADDI:
				{
				setState(31);
				match(ADDI);
				setState(32);
				match(REG);
				setState(33);
				match(NUMBER);
				}
				break;
			case SUB:
				{
				setState(34);
				match(SUB);
				setState(35);
				match(REG);
				setState(36);
				match(REG);
				}
				break;
			case SUBI:
				{
				setState(37);
				match(SUBI);
				setState(38);
				match(REG);
				setState(39);
				match(NUMBER);
				}
				break;
			case MUL:
				{
				setState(40);
				match(MUL);
				setState(41);
				match(REG);
				setState(42);
				match(REG);
				}
				break;
			case MULI:
				{
				setState(43);
				match(MULI);
				setState(44);
				match(REG);
				setState(45);
				match(NUMBER);
				}
				break;
			case DIV:
				{
				setState(46);
				match(DIV);
				setState(47);
				match(REG);
				setState(48);
				match(REG);
				}
				break;
			case DIVI:
				{
				setState(49);
				match(DIVI);
				setState(50);
				match(REG);
				setState(51);
				match(NUMBER);
				}
				break;
			case PUSH:
				{
				setState(52);
				match(PUSH);
				setState(55);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NUMBER:
					{
					setState(53);
					((InstructionContext)_localctx).n = match(NUMBER);
					}
					break;
				case LABEL:
					{
					setState(54);
					((InstructionContext)_localctx).l = match(LABEL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case PUSHR:
				{
				setState(57);
				match(PUSHR);
				setState(58);
				match(REG);
				}
				break;
			case POP:
				{
				setState(59);
				match(POP);
				}
				break;
			case POPR:
				{
				setState(60);
				match(POPR);
				setState(61);
				match(REG);
				}
				break;
			case BRANCH:
				{
				setState(62);
				match(BRANCH);
				setState(63);
				match(LABEL);
				}
				break;
			case BRANCHEQ:
				{
				setState(64);
				match(BRANCHEQ);
				setState(65);
				match(REG);
				setState(66);
				match(REG);
				setState(67);
				match(LABEL);
				}
				break;
			case BRANCHLESSEQ:
				{
				setState(68);
				match(BRANCHLESSEQ);
				setState(69);
				match(REG);
				setState(70);
				match(REG);
				setState(71);
				match(LABEL);
				}
				break;
			case JUMPSUB:
				{
				setState(72);
				match(JUMPSUB);
				setState(73);
				match(LABEL);
				}
				break;
			case RETURNSUB:
				{
				setState(74);
				match(RETURNSUB);
				setState(75);
				match(REG);
				}
				break;
			case LABEL:
				{
				setState(76);
				((InstructionContext)_localctx).l = match(LABEL);
				setState(77);
				match(T__2);
				}
				break;
			case HALT:
				{
				setState(78);
				match(HALT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001fR\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0001"+
		"\u0000\u0005\u0000\u0006\b\u0000\n\u0000\f\u0000\t\t\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u00018\b\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u0001P\b\u0001\u0001\u0001\u0000\u0000"+
		"\u0002\u0000\u0002\u0000\u0000g\u0000\u0007\u0001\u0000\u0000\u0000\u0002"+
		"O\u0001\u0000\u0000\u0000\u0004\u0006\u0003\u0002\u0001\u0000\u0005\u0004"+
		"\u0001\u0000\u0000\u0000\u0006\t\u0001\u0000\u0000\u0000\u0007\u0005\u0001"+
		"\u0000\u0000\u0000\u0007\b\u0001\u0000\u0000\u0000\b\u0001\u0001\u0000"+
		"\u0000\u0000\t\u0007\u0001\u0000\u0000\u0000\n\u000b\u0005\u0004\u0000"+
		"\u0000\u000b\f\u0005\u001a\u0000\u0000\f\r\u0005\u001c\u0000\u0000\r\u000e"+
		"\u0005\u0001\u0000\u0000\u000e\u000f\u0005\u001a\u0000\u0000\u000fP\u0005"+
		"\u0002\u0000\u0000\u0010\u0011\u0005\u0005\u0000\u0000\u0011\u0012\u0005"+
		"\u001a\u0000\u0000\u0012\u0013\u0005\u001c\u0000\u0000\u0013\u0014\u0005"+
		"\u0001\u0000\u0000\u0014\u0015\u0005\u001a\u0000\u0000\u0015P\u0005\u0002"+
		"\u0000\u0000\u0016\u0017\u0005\u0006\u0000\u0000\u0017\u0018\u0005\u001a"+
		"\u0000\u0000\u0018P\u0005\u001c\u0000\u0000\u0019\u001a\u0005\u0007\u0000"+
		"\u0000\u001a\u001b\u0005\u001a\u0000\u0000\u001bP\u0005\u001a\u0000\u0000"+
		"\u001c\u001d\u0005\b\u0000\u0000\u001d\u001e\u0005\u001a\u0000\u0000\u001e"+
		"P\u0005\u001a\u0000\u0000\u001f \u0005\t\u0000\u0000 !\u0005\u001a\u0000"+
		"\u0000!P\u0005\u001c\u0000\u0000\"#\u0005\n\u0000\u0000#$\u0005\u001a"+
		"\u0000\u0000$P\u0005\u001a\u0000\u0000%&\u0005\u000b\u0000\u0000&\'\u0005"+
		"\u001a\u0000\u0000\'P\u0005\u001c\u0000\u0000()\u0005\f\u0000\u0000)*"+
		"\u0005\u001a\u0000\u0000*P\u0005\u001a\u0000\u0000+,\u0005\r\u0000\u0000"+
		",-\u0005\u001a\u0000\u0000-P\u0005\u001c\u0000\u0000./\u0005\u000e\u0000"+
		"\u0000/0\u0005\u001a\u0000\u00000P\u0005\u001a\u0000\u000012\u0005\u000f"+
		"\u0000\u000023\u0005\u001a\u0000\u00003P\u0005\u001c\u0000\u000047\u0005"+
		"\u0010\u0000\u000058\u0005\u001c\u0000\u000068\u0005\u001b\u0000\u0000"+
		"75\u0001\u0000\u0000\u000076\u0001\u0000\u0000\u00008P\u0001\u0000\u0000"+
		"\u00009:\u0005\u0011\u0000\u0000:P\u0005\u001a\u0000\u0000;P\u0005\u0012"+
		"\u0000\u0000<=\u0005\u0013\u0000\u0000=P\u0005\u001a\u0000\u0000>?\u0005"+
		"\u0014\u0000\u0000?P\u0005\u001b\u0000\u0000@A\u0005\u0015\u0000\u0000"+
		"AB\u0005\u001a\u0000\u0000BC\u0005\u001a\u0000\u0000CP\u0005\u001b\u0000"+
		"\u0000DE\u0005\u0016\u0000\u0000EF\u0005\u001a\u0000\u0000FG\u0005\u001a"+
		"\u0000\u0000GP\u0005\u001b\u0000\u0000HI\u0005\u0017\u0000\u0000IP\u0005"+
		"\u001b\u0000\u0000JK\u0005\u0018\u0000\u0000KP\u0005\u001a\u0000\u0000"+
		"LM\u0005\u001b\u0000\u0000MP\u0005\u0003\u0000\u0000NP\u0005\u0019\u0000"+
		"\u0000O\n\u0001\u0000\u0000\u0000O\u0010\u0001\u0000\u0000\u0000O\u0016"+
		"\u0001\u0000\u0000\u0000O\u0019\u0001\u0000\u0000\u0000O\u001c\u0001\u0000"+
		"\u0000\u0000O\u001f\u0001\u0000\u0000\u0000O\"\u0001\u0000\u0000\u0000"+
		"O%\u0001\u0000\u0000\u0000O(\u0001\u0000\u0000\u0000O+\u0001\u0000\u0000"+
		"\u0000O.\u0001\u0000\u0000\u0000O1\u0001\u0000\u0000\u0000O4\u0001\u0000"+
		"\u0000\u0000O9\u0001\u0000\u0000\u0000O;\u0001\u0000\u0000\u0000O<\u0001"+
		"\u0000\u0000\u0000O>\u0001\u0000\u0000\u0000O@\u0001\u0000\u0000\u0000"+
		"OD\u0001\u0000\u0000\u0000OH\u0001\u0000\u0000\u0000OJ\u0001\u0000\u0000"+
		"\u0000OL\u0001\u0000\u0000\u0000ON\u0001\u0000\u0000\u0000P\u0003\u0001"+
		"\u0000\u0000\u0000\u0003\u00077O";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}