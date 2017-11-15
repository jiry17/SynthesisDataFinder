// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.searchlib.treenet;

import com.yahoo.searchlib.rankingexpression.RankingExpression;
import com.yahoo.searchlib.treenet.parser.ParseException;
import com.yahoo.searchlib.treenet.parser.TreeNetParser;
import junit.framework.TestCase;

import java.io.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen</a>
 */
public class TreeNetParserTestCase extends TestCase {

    private static final boolean WRITE_FILES = false;

    public void testRankingExpression() {
        for (int i = 1; i <= 8; ++i) {
            String inputFile  = String.format("src/test/files/treenet%02d.model", i);
            String outputFile = String.format("src/test/files/ranking%02d.expression", i);
            String input = readFile(inputFile);
            String expression = convertModel(inputFile, input);
            if (WRITE_FILES) {
                writeFile(outputFile, expression);
            }
            else {
                String output = readFile(outputFile);
                assertParseable(output, outputFile);
                assertEquals(output.trim(), expression);
            }
        }
    }

    private void assertParseable(String rankingExpressionString,String fileName) {
        try {
            new RankingExpression(rankingExpressionString);
        }
        catch (com.yahoo.searchlib.rankingexpression.parser.ParseException e) {
            throw new RuntimeException("Could not parse ranking expression in '" + fileName + "'",e);
        }
    }

    private String convertModel(String modelFile, String model) {
        try {
            TreeNetParser parser = new TreeNetParser(new StringReader(model));
            return parser.treeNet().toRankingExpression();
        } catch (ParseException e) {
            throw new AssertionError("In model " + modelFile + ": " + e.getMessage(), e);
        }
    }

    private String readFile(String file) {
        try {
            StringBuilder ret = new StringBuilder();
            BufferedReader in = new BufferedReader(new FileReader(file));
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    break;
                }
                ret.append(str).append("\n");
            }
            return ret.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private void writeFile(String file, String content) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(content);
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
