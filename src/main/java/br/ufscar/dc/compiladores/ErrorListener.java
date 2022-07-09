package br.ufscar.dc.compiladores;

import java.io.IOException;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class ErrorListener extends BaseErrorListener {

    public static final ErrorListener INSTANCE = new ErrorListener();

   @Override
   public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e){
        Token t = (Token) offendingSymbol;
        String type = LaLexer.VOCABULARY.getDisplayName(t.getType());
        try {
            // O EOF aparece entre <>, portanto esse if é necessário para retirá-los
            if(t.getText() == "<EOF>"){
                Output.myWriter.write("Linha "+line+": erro sintatico proximo a " + "EOF" + '\n');
            }
            // Se for um token desconhecido, aponta a linha em que ele aparece e avisa do erro
            else if(type == "UNKNOWN_TOKEN"){
                Output.myWriter.write("Linha " + t.getLine() + ": " + t.getText() + " - simbolo nao identificado\n");
            // Caso haja um comentário não fechado, avisa a linha do erro
            } else  if(type == "COMMENT_ERROR"){
                Output.myWriter.write("Linha " + t.getLine() + ": comentario nao fechado\n");
            // Caso haja uma cadeia não fechada, avisa a linha do erro
            } else if(type == "CADEIA_ERROR"){
                Output.myWriter.write("Linha " + t.getLine() + ": cadeia literal nao fechada\n");
            }
            // Se não for nenhum dos erros léxixos conhecidos, considera como um erro de parsing e avisa do erro
            else
                Output.myWriter.write("Linha "+line+": erro sintatico proximo a " + t.getText() + '\n');


            // A compilação deve ser interrompida quando o primeiro erro é encontrado
            Output.myWriter.write("Fim da compilacao\n");
            Output.myWriter.close();
            System.exit(0);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}