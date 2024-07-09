package svm;

import java.util.HashMap;

public class SVMVisitorImpl extends SVMBaseVisitor<Void> {	
	
    public AssemblyClass[] code = new AssemblyClass[ExecuteVM.CODESIZE];    
    private int i = 0;
    private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
    	// definisce a quale linea  di codice corrisponde una etichetta
    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();
    	// definisce l'insieme di linee di codice che contengono una certa etichetta
    
    public Void visitAssembly(SVMParser.AssemblyContext ctx) { 
    		visitChildren(ctx);
     		
    		//printlabeladd() ; 			// checks
    		//printlabelref() ; 			// checks
    		
    		for (Integer refAdd : labelRef.keySet()) {
    			int tmp = refAdd ;
    			String s = labelRef.get(refAdd) ;
                if (code[tmp] == null) {
                	code[tmp] =  new AssemblyClass(labelAdd.get(s), null, null, null);
                } else {
					System.out.println(labelRef);
                	code[tmp].setArg1(labelAdd.get(s).toString()); 
                }
            }

    		return null;
    }
    
    public void printlabeladd() {
    	for (String m : labelAdd.keySet()) {
    		System.out.println("chiave: " + m + " valore: " + labelAdd.get(m)) ;
    	}
    }
    
    public void printlabelref() {
    	for (Integer m : labelRef.keySet()) {
    		System.out.println("indirizzo: " + m + " etichetta: " + labelRef.get(m)) ;
    	}
    }
    
    public Void visitInstruction(SVMParser.InstructionContext ctx) { 
    	switch (ctx.getStart().getType()) {
    		case SVMLexer.LOAD:
     			code[i] = new AssemblyClass(SVMParser.LOAD, ctx.REG(0).toString(), ctx.NUMBER().toString(),ctx.REG(1).toString());
    			i = i+1 ;
    			break;
      		case SVMLexer.STORE:
       			code[i] = new AssemblyClass(SVMParser.STORE, ctx.REG(0).toString(), ctx.NUMBER().toString(),ctx.REG(1).toString());
    			i = i+1 ;
    			break;
			case SVMLexer.STOREI:
				code[i] = new AssemblyClass(SVMParser.STOREI, ctx.REG(0).toString(), ctx.NUMBER().toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.MOVE:
				code[i] = new AssemblyClass(SVMParser.MOVE, ctx.REG(0).toString(), ctx.REG(1).toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.ADD:
				code[i] = new AssemblyClass(SVMParser.ADD, ctx.REG(0).toString(), ctx.REG(1).toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.ADDI:
				code[i] = new AssemblyClass(SVMParser.ADDI, ctx.REG(0).toString(), ctx.NUMBER().toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.SUB:
				code[i] = new AssemblyClass(SVMParser.SUB, ctx.REG(0).toString(), ctx.REG(1).toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.SUBI:
				code[i] = new AssemblyClass(SVMParser.SUBI, ctx.REG(0).toString(), ctx.NUMBER().toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.MUL:
				code[i] = new AssemblyClass(SVMParser.MUL, ctx.REG(0).toString(), ctx.REG(1).toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.MULI:
				code[i] = new AssemblyClass(SVMParser.MULI, ctx.REG(0).toString(), ctx.NUMBER().toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.DIV:
				code[i] = new AssemblyClass(SVMParser.DIV, ctx.REG(0).toString(), ctx.REG(1).toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.DIVI:
				code[i] = new AssemblyClass(SVMParser.DIVI, ctx.REG(0).toString(), ctx.NUMBER().toString(), null);
    			i = i+1 ;
				break;
			case SVMLexer.PUSH:
				if (ctx.n != null) {
					code[i] = new AssemblyClass(SVMParser.PUSH, ctx.n.getText(), null, null);
				} else {
					code[i] = new AssemblyClass(SVMParser.PUSH, ctx.l.getText(), null, null);
					labelRef.put(i, ctx.l.getText());
				}
    			i = i+1 ;
				break;
			case SVMLexer.PUSHR:
				code[i] = new AssemblyClass(SVMParser.PUSHR, ctx.REG(0).toString(), null, null);
    			i = i+1 ;
				break;
			case SVMLexer.POP:
				code[i] = new AssemblyClass(SVMParser.POP, null, null, null);
    			i = i+1 ;
				break;
			case SVMLexer.POPR:
				code[i] = new AssemblyClass(SVMParser.POPR, ctx.REG(0).toString(), null, null);
    			i = i+1 ;
				break;		
			case SVMLexer.LABEL:
				labelAdd.put(ctx.l.getText(),i);
				break;
			case SVMLexer.BRANCH:
				code[i] = new AssemblyClass(SVMParser.BRANCH, ctx.LABEL().toString(), null, null);
				i = i+1 ;
	            labelRef.put(i, (ctx.LABEL() != null ? ctx.LABEL().toString() : null));
	            i = i+1 ;
	            break;
			case SVMLexer.BRANCHEQ:
				code[i] = new AssemblyClass(SVMParser.BRANCHEQ, ctx.REG(0).toString(), ctx.REG(1).toString(), ctx.LABEL().toString());
				i = i+1 ;
	            labelRef.put(i, (ctx.LABEL() != null ? ctx.LABEL().toString() : null));
	            i = i+1 ;
                break;
			case SVMLexer.BRANCHLESSEQ:
				code[i] = new AssemblyClass(SVMParser.BRANCHLESSEQ, ctx.REG(0).toString(), ctx.REG(1).toString(), ctx.LABEL().toString());
				i = i+1 ;
	            labelRef.put(i, (ctx.LABEL() != null ? ctx.LABEL().toString() : null));
	            i = i+1 ;
                break;
			case SVMLexer.JUMPSUB:
				code[i] = new AssemblyClass(SVMParser.JUMPSUB, ctx.LABEL().toString(), null, null);
	            labelRef.put(i, ctx.LABEL().toString() );
	            i = i+1 ;
				break;
			case SVMLexer.RETURNSUB:
				code[i] = new AssemblyClass(SVMParser.RETURNSUB, ctx.REG(0).toString(), null, null);
    			i = i+1 ;
				break;
			case SVMLexer.HALT:
				code[i] = new AssemblyClass(SVMParser.HALT, null, null, null);
    			i = i+1 ;
				break;             
			default:
	            break;	// Invalid instruction
    	}
    	return null;
    }

}
