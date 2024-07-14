# CLP Project

Project for the AA 2023/2024 **Complementi di programmazione**'s course of the Master Degree in Computer Science.

## How to build

You first need to build the project using the Makefile.

```
make build
```

## Run

You can run the software using the following command

```
java -cp lib/antlr-4.13.1-complete.jar:out Main <file.py>
```

you also can use three flags on the execution:

- `--optimize`: runs the optimizer for the file `<file.py>`. The optimized
  version is auto saved at `./optimized.py`.

- `--tree`: shows the AST on video and on JFrame.

- `--exec`: executes the SVM for the Assembly code generated in `code.asm`.

You can use all the three flags togheter if you want to.

## Example

- Optimizer 

```
$ java -cp lib/antlr-4.13.1-complete.jar:out Main test/2a.py --optimize
n=int(input())
x=int(input())
m=1
tmp=2 * x
while n>1:

        m=m*tmp
        n=n-1


print(m+tmp)

Saving optimized file...
Everything is OK!
```

- Tree

```
$ java -cp lib/antlr-4.13.1-complete.jar:out Main test/2a.py --tree
Root
  SimpleStmts
    SimpleStmt
      Assignment
        ExprList
          Expr
            AtomNode: n
        Augassign(=)
        ExprList
          Expr
            AtomNode: int
            TrailerNode
              ArglistNode
                Expr
      Assignment                                                                                                                                                                                             [40/225]
    [...] 
    SimpleStmt
      Expr
        AtomNode: print
        TrailerNode
          ArglistNode
            Expr
              Expr
                AtomNode: m
              Expr
                AtomNode: tmp
              Op(+)

Everything is OK!
```

- Exec

```
$ java -cp lib/antlr-4.13.1-complete.jar:out Main test/3-1.py --exec
Creating VM code...
Executing assemply code...
0: 17 ----- SP = 999, FP = 999, AL = 998, RA = 0, A0 = 0, T1 = 0
1: 17	999 ----- SP = 998, FP = 999, AL = 998, RA = 0, A0 = 0, T1 = 0
2: 17	999	998 ----- SP = 997, FP = 999, AL = 998, RA = 0, A0 = 0, T1 = 0
3: 7	999	998	999 ----- SP = 996, FP = 999, AL = 998, RA = 0, A0 = 0, T1 = 0
4: 9	999	998	999 ----- SP = 996, FP = 996, AL = 998, RA = 0, A0 = 0, T1 = 0
5: 7	999	998	999 ----- SP = 996, FP = 997, AL = 998, RA = 0, A0 = 0, T1 = 0
[...]
216: 18	999	998	999	998	29	2	14	997	996	29	3	140	992	991 ----- SP = 985, FP = 987, AL = 986, RA = 140, A0 = 1, T1 = 1
217: 5	999	998	999	998	29	2	14	997	996	29	3	140	992 ----- SP = 986, FP = 987, AL = 986, RA = 140, A0 = 1, T1 = 1
218: 7	999	998	999	998	29	2	14	997	996	29	3	140	992 ----- SP = 986, FP = 992, AL = 986, RA = 140, A0 = 1, T1 = 1
219: 11	999	998	999	998	29	2	14	997	996	29	3	140	992 ----- SP = 986, FP = 992, AL = 992, RA = 140, A0 = 1, T1 = 1
220: 18	999	998	999	998	29	2	14	997	996	29	3	140	992 ----- SP = 986, FP = 992, AL = 991, RA = 140, A0 = 1, T1 = 1
221: 24	999	998	999	998	29	2	14	997	996	29	3	140 ----- SP = 987, FP = 992, AL = 991, RA = 140, A0 = 1, T1 = 1
222: 19	999	998	999	998	29	2	14	997	996	29	3	140 ----- SP = 987, FP = 992, AL = 991, RA = 140, A0 = 1, T1 = 1
223: 9	999	998	999	998	29	2	14	997	996	29	3 ----- SP = 988, FP = 992, AL = 991, RA = 140, A0 = 1, T1 = 1
224: 18	999	998	999	998	29	2	14	997	996 ----- SP = 990, FP = 992, AL = 991, RA = 140, A0 = 1, T1 = 1
225: 5	999	998	999	998	29	2	14	997 ----- SP = 991, FP = 992, AL = 991, RA = 140, A0 = 1, T1 = 1
226: 7	999	998	999	998	29	2	14	997 ----- SP = 991, FP = 997, AL = 991, RA = 140, A0 = 1, T1 = 1
227: 11	999	998	999	998	29	2	14	997 ----- SP = 991, FP = 997, AL = 997, RA = 140, A0 = 1, T1 = 1
228: 18	999	998	999	998	29	2	14	997 ----- SP = 991, FP = 997, AL = 996, RA = 140, A0 = 1, T1 = 1
229: 24	999	998	999	998	29	2	14 ----- SP = 992, FP = 997, AL = 996, RA = 140, A0 = 1, T1 = 1
230: 19	999	998	999	998	29	2	14 ----- SP = 992, FP = 997, AL = 996, RA = 140, A0 = 1, T1 = 1
231: 9	999	998	999	998	29	2 ----- SP = 993, FP = 997, AL = 996, RA = 14, A0 = 1, T1 = 1
232: 18	999	998	999	998 ----- SP = 995, FP = 997, AL = 996, RA = 14, A0 = 1, T1 = 1
233: 5	999	998	999 ----- SP = 996, FP = 997, AL = 996, RA = 14, A0 = 1, T1 = 1
234: 7	999	998	999 ----- SP = 996, FP = 999, AL = 996, RA = 14, A0 = 1, T1 = 1
235: 11	999	998	999 ----- SP = 996, FP = 999, AL = 999, RA = 14, A0 = 1, T1 = 1
236: 18	999	998	999 ----- SP = 996, FP = 999, AL = 998, RA = 14, A0 = 1, T1 = 1
237: 24	999	998 ----- SP = 997, FP = 999, AL = 998, RA = 14, A0 = 1, T1 = 1
238: 18	999	998 ----- SP = 997, FP = 999, AL = 998, RA = 14, A0 = 1, T1 = 1
239: 18	999 ----- SP = 998, FP = 999, AL = 998, RA = 14, A0 = 1, T1 = 1
240: 25 ----- SP = 999, FP = 999, AL = 998, RA = 14, A0 = 1, T1 = 1

Result: 1

Everything is OK!
```
