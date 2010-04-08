package com.tiemens.secretshare.math;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.tiemens.secretshare.math.EasyLinearEquation.EasySolve;

import junit.framework.Assert;
import junit.framework.TestCase;

public class EasyLinearEquationUT
    extends TestCase
{


 // ==================================================
    // class static data
    // ==================================================

    // ==================================================
    // class static methods
    // ==================================================

    // ==================================================
    // instance data
    // ==================================================

    // ==================================================
    // factories
    // ==================================================

    // ==================================================
    // constructors
    // ==================================================

    protected void setUp()
        throws Exception
    {
        super.setUp();
    }

    // ==================================================
    // public methods
    // ==================================================

    public void testFirst()
    {
        EasyLinearEquation ele = null;
        
        EasyLinearEquation
            .create(new int[][]
                   {
                    { 10, 83,   32,   23},
                    { 44,  5,   13,   22},
                    { 59,  31,  82,   99}
                   });
        
        ele = EasyLinearEquation
        .create(new int[][]
                          {
                           { 33, 1,   2,   3},
                           { 81, 4,   5,   6},
                           { 52, 3,   2,   4}
                          });
        
        EasySolve solve = ele.solve();
        Assert.assertEquals("1 should be 6", 
                            BigInteger.valueOf(6), solve.getAnswer(1));
        Assert.assertEquals("2 should be 3", 
                            BigInteger.valueOf(3), solve.getAnswer(2));
        Assert.assertEquals("3 should be 7", 
                            BigInteger.valueOf(7), solve.getAnswer(3));
    }
    
    public void testJavadocExample() 
        throws SecurityException, IOException
    {
        if (true)
        {
            enableLogging();
        }
        
        EasyLinearEquation ele = null;
        
        ele = EasyLinearEquation
            .create(new int[][]
                   {
                    { 1491,  83,   32,   22},
                    { 329,    5,   13,   22},
                    { 122,    3,    2,   19}
                   });
        EasySolve solve = ele.solve();
        System.out.println("Output testJavadocExample test case.");
        System.out.println("answer(1)=" + solve.getAnswer(1));
        System.out.println("answer(2)=" + solve.getAnswer(2));
        System.out.println("answer(3)=" + solve.getAnswer(3));
    }

    
    public void testFromRealSecret() 
        throws SecurityException, IOException
    {
        if (true)
        {
            enableLogging();
        }
    
        EasyLinearEquation ele = null;

//        FINE: 45662,1,1,1
//        FINE: 45640,1,2,4
//        FINE: 45675,1,3,9
//        EQ: PolyEqImpl[ 45654 + x^1 * 1   + x^2 * 7 
//        EQ: PolyEqImpl[ 45654 + x^1 * -9  + x^2 * 1  
//        EQ: PolyEqImpl[ 45654 + x^1 * -14 + x^2 * 7  

        ele = EasyLinearEquation
            .create(new int[][]
               {
                { 45662,1,1,1},
                { 45684,1,2,4},
                { 45720,1,3,9}
               });
        EasySolve solve = ele.solve();
        System.out.println("Output testReal test case.");
        System.out.println("answer(1)=" + solve.getAnswer(1));
        System.out.println("answer(2)=" + solve.getAnswer(2));
        System.out.println("answer(3)=" + solve.getAnswer(3));
    }

    /**
     * Test case when coeffs come from random-long.
     * 
     */
    public void testFromRealSecretLong() 
        throws SecurityException, IOException
    {
        if (true)
        {
            enableLogging();
        }
        
        EasyLinearEquation ele = null;

        //    EQ: PolyEqImpl[ 45654 + x^1 * -6519408338692630574 + x^2 * -897291810407650440 
        // mod=59561
        //  BigInteger modulus = BigInteger.valueOf(59561);
        ele = EasyLinearEquation
            .create(new int[][]
                              {
                    { 24689,1,1,1},
                    { 34394,1,2,4},
                    { 15208,1,3,9}
                              });
        // ele = ele.convertToModulus(modulus);
        EasySolve solve = ele.solve();
        System.out.println("Output testReal test case.");
        System.out.println("answer(1)=" + solve.getAnswer(1));
        System.out.println("answer(2)=" + solve.getAnswer(2));
        System.out.println("answer(3)=" + solve.getAnswer(3));
    }
    
    
    public void testMod()
    {
        BigInteger a = BigInteger.valueOf(-28891);
        BigInteger b = BigInteger.valueOf(59561);
        System.out.println("a mod b = " + a.mod(b));
    }
    

    /**
     * Taken from wikipedia example at Shamir's_Secret_Sharing.
     * This is a n=6, k=3 example, with a secret of '1234' 
     * f(x) = 1234 + 166x + 94x^2
     * 
     * f(1) = 1494   f(2) = 1942   f(3) = 2578   f(4) = 3402   f(5) = 4414   f(6) = 5614
     * The page selects secrets numbered: 2, 4 and 5
     */
    public void testFirstPolynomial()
    {
        EasyLinearEquation ele = null;
        
        BigInteger[] xarray = new BigInteger[] 
                                             {
                BigInteger.valueOf(2),
                BigInteger.valueOf(4),
                BigInteger.valueOf(5),
                                             };
        
        BigInteger[] fofxarray = new BigInteger[] 
                                             {
                BigInteger.valueOf(1942),
                BigInteger.valueOf(3402),
                BigInteger.valueOf(4414),
                                             };
        ele = EasyLinearEquation.createForPolynomial(xarray, fofxarray);
        
        EasySolve solve = ele.solve();
        Assert.assertEquals("1 should be 1234", 
                            BigInteger.valueOf(1234), solve.getAnswer(1));
        Assert.assertEquals("2 should be 166", 
                            BigInteger.valueOf(166), solve.getAnswer(2));
        Assert.assertEquals("3 should be 94", 
                            BigInteger.valueOf(94), solve.getAnswer(3));
    }
    

    // ==================================================
    // non public methods
    // ==================================================

    public static void enableLogging()
    {
        // example code to turn on ALL logging 
        //
        // To see logging:
        // [a] set the handler's level
        // [b] add the handler
        // [c] set the logger's level
        
        Logger l = EasyLinearEquation.logger;
        Handler lh = new ConsoleHandler();
        lh.setFormatter(oneLineFormatter());
        // don't forget to do this:
        lh.setLevel(Level.ALL);
        
        // alternative: write log to file:
        //lh = new FileHandler("log.txt");
        
        // need this too:
        l.addHandler(lh);
        // and this:
        l.setLevel(Level.ALL);
        if (EasyLinearEquation.logger.isLoggable(Level.FINE))
        {
            System.out.println("ok");
            EasyLinearEquation.logger.fine("Hi there");
        }
        else
        {
            System.out.println("failed");
        }
    }

    public static Formatter oneLineFormatter()
    {
        return new SimpleFormatter()
        {
            public synchronized String format(LogRecord record) 
            {
                StringBuffer sb = new StringBuffer();
                String message = formatMessage(record);
                //sb.append(record.getLevel().getLocalizedName());
                //sb.append(": ");
                sb.append(message);
                sb.append("\n");
                if (record.getThrown() != null) {
                    try {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        record.getThrown().printStackTrace(pw);
                        pw.close();
                    sb.append(sw.toString());
                    } catch (Exception ex) {
                    }
                }
                return sb.toString();
                }
            
        };
    }
}