WHILE AP cond CP sent
----------------------

EX: while ( 1 == 1 ) print(0);

L0:
  if ( cond ) goto L1;
  sent;
  goto L0;
L1:

----------------------
IF AP cond CP sent
----------------------

EX: if ( 1 == 1 ) print(0);

  if ( cond ) goto L0;
  goto L1;
L0:
  sent;
L1:

----------------------------
IF AP cond CP sent ELSE sent
----------------------------

EX: if ( 1 == 1 ) print(0); else print(1);

  if ( cond ) goto L0;
  goto L1;
L0:
  sent1;
L1:
  sent2;

-------------------------------------
FOR AP sent PYC cond PYC sent CP sent
-------------------------------------

EX: for(i=0; i < 3; i = i+1) print(0);

sent1;
L0:
  if ( cond ) goto L1;
  sent3;
  sent2;
  goto L0;
L1:
