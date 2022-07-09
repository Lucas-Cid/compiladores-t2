package br.ufscar.dc.compiladores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class App 
{
    public static void main( String[] args )
    {
        try {
            // Abre arquivo de entrada
            CharStream cs = CharStreams.fromFileName(args[0]);

            // Coloca o arquivo no lexer
            LaLexer lexer = new LaLexer(cs);

            // Cria uma classe personalizada para lidar com os erros
            // Usa a classe personalizada para lidar com os erros do lexer
            lexer.removeErrorListeners();
            lexer.addErrorListener(ErrorListener.INSTANCE);

            // Cria arquivo de saída e eventuais diretórios necessários
            String path = args[1];
            File file = new File(path);
            file.getParentFile().mkdirs();
            file.createNewFile();

            // Abre arquivo para escrita 
            // Criei uma classe para escrever no arquivo de saída
            // Isso facilita o acesso ao arquivo na classe que cuida dos erros
            Output.myWriter  = new FileWriter(file);

            // Cria o parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LaParser parser = new LaParser(tokens);

            // Usa a classe personalizada para lidar com os erros do parser
            parser.removeErrorListeners();
            parser.addErrorListener(ErrorListener.INSTANCE);
            parser.programa();

            Output.myWriter.write("Fim da compilacao\n");

            Output.myWriter.close();
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }
}
