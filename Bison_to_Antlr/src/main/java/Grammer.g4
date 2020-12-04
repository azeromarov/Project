grammar Grammer;
start: input EOF;

input: instructions;
instructions: instruction
        | instructions instruction;

list_of_id_args:
            | id
            | id '=' no_comma_expr
            | FESPACE id
            | type_of_dcl id
            | type_of_dcl '&' id
            | '[' list_of_id_args ']'
            | list_of_id_args ',' id
            | list_of_id_args ',' '[' list_of_id_args ']'
            | list_of_id_args ',' id '=' no_comma_expr
            | list_of_id_args ',' FESPACE id
            | list_of_id_args ',' type_of_dcl id
            | list_of_id_args ',' type_of_dcl '&' id ;

list_of_id1: id
            | list_of_id1 ',' id;

id: ID | FESPACE;

list_of_dcls: ID
            | ID '=' no_comma_expr
            | ID '(' parameters_list ')'
            | list_of_dcls ',' list_of_dcls;

parameters_list
            : no_set_expr
            | FESPACE ID
            | ID '=' no_set_expr
            | parameters_list ',' no_set_expr
            | parameters_list ',' id '=' no_set_expr ;
type_of_dcl: TYPE
            | TYPE '[' TYPE ']' ;
id_space:
                ID
            | ID '[' no_set_expr ']'
            | ID '=' no_set_expr
            | '[' list_of_id1 ']'
            | '[' list_of_id1 ']' '[' no_set_expr ']'
            | '[' list_of_id1 ']' '=' no_set_expr ;

id_array_space:
                ID '(' no_set_expr ')'
            | '[' list_of_id1 ']' '(' no_set_expr ')' ;

fespace: FESPACE;

spaceIDa : id_array_space
            | spaceIDa ',' id_array_space ;

spaceIDb : id_space
            | spaceIDb ',' id_space ;

spaceIDs : fespace spaceIDb
            | fespace '[' TYPE ']' spaceIDa ;

fespace_def: ID '(' parameters_list ')' ;

fespace_def_list: fespace_def
            | fespace_def_list ',' fespace_def ;

declaration: type_of_dcl list_of_dcls ';'
            | 'fespace' fespace_def_list ';'
            | spaceIDs ';'
            | FUNCTION ID '=' expr ';'
            | FUNCTION type_of_dcl ID '(' list_of_id_args ')' '{' instructions'}'
            | FUNCTION ID '(' list_of_id_args ')' '=' no_comma_expr ';' ;

begin: '{' ;
end: '}' ;

for_loop: 'for';
while_loop: 'while';

instruction: ';'
            | 'include' STRING
            | expr ';'
            | declaration
            | for_loop '(' expr ';' expr ';' expr ')' instruction
            | while_loop '(' expr ')' instruction
            | 'if' '(' expr ')' instruction
            | 'if' '(' expr ')' instruction ELSE instruction
            | begin instructions end
            | 'border' ID border_expr
            | 'border' ID '[' array ']' ';'
            | 'break' ';'
            | 'continue' ';'
            | 'return' expr ';' ;

bornes: '(' ID '=' expr ',' expr ')' ;

border_expr: bornes instruction ;

expr
            : no_comma_expr
            | expr ',' expr ;

unop
            : '-'
            | '+'
            | '!'
            | '++'
            | '--' ;

no_comma_expr
            : no_set_expr
            | no_set_expr '=' no_comma_expr
            | no_set_expr '+=' no_comma_expr
            | no_set_expr '-=' no_comma_expr
            | no_set_expr '*=' no_comma_expr
            | no_set_expr '/=' no_comma_expr ;

no_set_expr:
              unary_expr
            | no_set_expr '*' no_set_expr
            | no_set_expr '.*' no_set_expr
            | no_set_expr './' no_set_expr
            | no_set_expr '/' no_set_expr
            | no_set_expr '%' no_set_expr
            | no_set_expr '+' no_set_expr
            | no_set_expr '-' no_set_expr
            | no_set_expr '<<' no_set_expr
            | no_set_expr '>>' no_set_expr
            | no_set_expr '&' no_set_expr
            | no_set_expr '&&' no_set_expr
            | no_set_expr '|' no_set_expr
            | no_set_expr '||' no_set_expr
            | no_set_expr '<' no_set_expr
            | no_set_expr '<=' no_set_expr
            | no_set_expr '>' no_set_expr
            | no_set_expr '>=' no_set_expr
            | no_set_expr '==' no_set_expr
            | no_set_expr '!=' no_set_expr ;

parameters:
            | no_set_expr
            | FESPACE
            | id '=' no_set_expr
            | parameters ',' FESPACE
            | parameters ',' no_set_expr
            | parameters ',' id '=' no_set_expr ;

array: no_comma_expr
            | array ',' no_comma_expr ;

unary_expr:
              pow_expr
            | unop pow_expr ;

pow_expr: primary
            | primary 'ˆ' unary_expr
            | primary '_' unary_expr
            | primary '´' ; // transpose

primary:
              ID
            | LNUM
            | DNUM
            | CNUM
            | STRING
            | primary '(' parameters ')'
            | primary '[' expr ']'
            | primary '[' ']'
            | primary '.' ID
            | primary '++'
            | primary '--'
            | TYPE '(' expr ')'
            | '(' expr ')'
            | '[' array ']' ;

ID       : [a-zA-Z] | [a-zA-Z][a-zA-Z0-9_]+ ;
FESPACE  : [' '|\n|\t] -> skip;
DNUM     :[0-9]+ ('.' [0-9]+)? ;
STRING   : '"' .*? '"' ;
FUNCTION : [a-zA-Z]+ ;
TYPE     : [a-zA-Z]+ ;