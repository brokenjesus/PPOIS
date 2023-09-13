package by.lupach.bigint.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BigIntTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Test
    void testSumFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-123");
        BigInt secondNum = new BigInt("124");
        assertEquals("1", firstNum.sum(secondNum).getVariableValue());
    }

    @Test
    void testSumSecondNumIsNegative() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("-124");
        assertEquals("-1", firstNum.sum(secondNum).getVariableValue());
    }

    @Test
    void testSumBothNumsAreNegative() {
        BigInt firstNum = new BigInt("-123");
        BigInt secondNum = new BigInt("-124");
        assertEquals("-247", firstNum.sum(secondNum).getVariableValue());
    }

    @Test
    void testSumSecondNumIsPositiveInt() {
        BigInt firstNum = new BigInt("122");
        int secondNum = 1;
        assertEquals("123", firstNum.sum(secondNum).getVariableValue());
    }

    @Test
    void testSumSecondNumIsNegativeInt() {
        BigInt firstNum = new BigInt("124");
        int secondNum = -1;
        assertEquals("123", firstNum.sum(secondNum).getVariableValue());
    }

    @Test
    void testSumDozen() {
        BigInt firstNum = new BigInt("999999");
        int secondNum = 1;
        assertEquals("1000000", firstNum.sum(secondNum).getVariableValue());
    }

    @Test
    void testIncrement() {
        BigInt firstNum = new BigInt("999999");
        firstNum.increment();
        assertEquals("1000000", firstNum.getVariableValue());
    }

    @Test
    void testDecrement() {
        BigInt firstNum = new BigInt("1000000");
        firstNum.decrement();
        assertEquals("999999", firstNum.getVariableValue());
    }

    @Test
    void testDifferenceFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-113");
        BigInt secondNum = new BigInt("10");
        assertEquals("-123", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testDifferenceSecondNumIsNegative() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("-132");
        assertEquals("255", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testDifferenceBothNumsAreNegative() {
        BigInt firstNum = new BigInt("-123");
        BigInt secondNum = new BigInt("-124");
        assertEquals("1", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testDifferenceSecondNumIsPositiveInt() {
        BigInt firstNum = new BigInt("124");
        int secondNum = 1;
        assertEquals("123", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testDifferenceSecondNumIsNegativeInt() {
        BigInt firstNum = new BigInt("124");
        int secondNum = -1;
        assertEquals("125", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testDifferenceSecondNumIsBigger() {
        BigInt firstNum = new BigInt("124");
        BigInt secondNum = new BigInt("125");
        assertEquals("-1", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testDifferenceDozen() {
        BigInt firstNum = new BigInt("1000000");
        int secondNum = 1;
        assertEquals("999999", firstNum.difference(secondNum).getVariableValue());
    }

    @Test
    void testMultiply() {
        BigInt firstNum = new BigInt("12345679");
        BigInt secondNum = new BigInt("9");
        assertEquals("111111111", firstNum.multiply(secondNum).getVariableValue());
    }

    @Test
    void testMultiplyFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-12345679");
        BigInt secondNum = new BigInt("9");
        assertEquals("-111111111", firstNum.multiply(secondNum).getVariableValue());
    }

    @Test
    void testMultiplySecondNumIsNegative() {
        BigInt firstNum = new BigInt("12345679");
        BigInt secondNum = new BigInt("-9");
        assertEquals("-111111111", firstNum.multiply(secondNum).getVariableValue());
    }

    @Test
    void testMultiplyBothFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-12345679");
        BigInt secondNum = new BigInt("-9");
        assertEquals("111111111", firstNum.multiply(secondNum).getVariableValue());
    }

    @Test
    void testMultiplySecondNumIsInt() {
        BigInt firstNum = new BigInt("-12345679");
        int secondNum = 9;
        assertEquals("-111111111", firstNum.multiply(secondNum).getVariableValue());
    }

    @Test
    void testMultiplyDozen() {
        BigInt firstNum = new BigInt("10");
        BigInt secondNum = new BigInt("999");
        assertEquals("9990", firstNum.multiply(secondNum).getVariableValue());
    }

    @Test
    void testDivision() {
        BigInt firstNum = new BigInt("111111111");
        BigInt secondNum = new BigInt("9");
        assertEquals("12345679", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-111111111");
        BigInt secondNum = new BigInt("9");
        assertEquals("-12345679", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionSecondNumIsNegative() {
        BigInt firstNum = new BigInt("111111111");
        BigInt secondNum = new BigInt("-9");
        assertEquals("-12345679", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionBothAreNegative() {
        BigInt firstNum = new BigInt("-111111111");
        BigInt secondNum = new BigInt("-9");
        assertEquals("12345679", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionSecondIsPositiveInt() {
        BigInt firstNum = new BigInt("111111111");
        int secondNum = 9;
        assertEquals("12345679", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionSecondIsNegativeInt() {
        BigInt firstNum = new BigInt("111111111");
        int secondNum = -9;
        assertEquals("-12345679", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionDividendBiggerThanDivisor() {
        BigInt firstNum = new BigInt("1");
        BigInt secondNum = new BigInt("1111");
        assertEquals("0", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testDivisionByZero() {
        BigInt firstNum = new BigInt("100");
        BigInt secondNum = new BigInt("0");
        Exception exception = assertThrows(Exception.class, () -> firstNum.division(secondNum));
        assertEquals("Division by zero", exception.getMessage());
    }

    @Test
    void testDivisionDozen() {
        BigInt firstNum = new BigInt("300000000");
        BigInt secondNum = new BigInt("3");
        assertEquals("100000000", firstNum.division(secondNum).getVariableValue());
    }

    @Test
    void testIsBiggerTrue() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("122");
        assertEquals(true, firstNum.isBigger(secondNum));
    }

    @Test
    void testIsBiggerFalse() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("124");
        assertEquals(false, firstNum.isBigger(secondNum));
    }

    @Test
    void testIsBiggerFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-123");
        BigInt secondNum = new BigInt("122");
        assertEquals(false, firstNum.isBigger(secondNum));
    }

    @Test
    void testIsBiggerSecondNumIsNegative() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("-124");
        assertEquals(true, firstNum.isBigger(secondNum));
    }

    @Test
    void testIsBiggerBothNegativeFirstDigitOfFirstNumIsBigger(){
        BigInt a = new BigInt("-9");
        BigInt b = new BigInt("-8");
        assertEquals(false, a.isBigger(b));
    }

    @Test
    void testIsBiggerBothNegativeFirstDigitOfSecondNumIsBigger(){
        BigInt a = new BigInt("-8");
        BigInt b = new BigInt("-9");
        assertEquals(true, a.isBigger(b));
    }

    @Test
    void testIsBiggerThanInt() {
        BigInt firstNum = new BigInt("123");
        int secondNum = -124;
        assertEquals(true, firstNum.isBigger(secondNum));
    }


    @Test
    void testIsSmallerFalse() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("122");
        assertEquals(false, firstNum.isSmaller(secondNum));
    }

    @Test
    void testIsSmallerTrue() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("124");
        assertEquals(true, firstNum.isSmaller(secondNum));
    }

    @Test
    void testIsSmallerFirstNumIsNegative() {
        BigInt firstNum = new BigInt("-123");
        BigInt secondNum = new BigInt("122");
        assertEquals(true, firstNum.isSmaller(secondNum));
    }

    @Test
    void testIsSmallerSecondNumIsNegative() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("-124");
        assertEquals(false, firstNum.isSmaller(secondNum));
    }

    @Test
    void testIsSmallerBothNegativeFirstDigitOfFirstNumIsBigger(){
        BigInt a = new BigInt("-9");
        BigInt b = new BigInt("-8");
        assertEquals(true, a.isSmaller(b));
    }

    @Test
    void testIsSmallerBothNegativeFirstDigitOfSecondNumIsBigger(){
        BigInt a = new BigInt("-8");
        BigInt b = new BigInt("-9");
        assertEquals(false, a.isSmaller(b));
    }

    @Test
    void testIsSmallerThanInt() {
        BigInt firstNum = new BigInt("123");
        int secondNum = -124;
        assertEquals(false, firstNum.isSmaller(secondNum));
    }

    @Test
    void testIsSmallerFirstNumNegativeAndFirstDigitFirstNumBiggerThanFirstDigitSecondNum() {
        BigInt firstNum = new BigInt("-123");
        BigInt secondNum = new BigInt("233");
        assertEquals(true, firstNum.isSmaller(secondNum));
    }


    @Test
    void testIsEqualTrue() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("123");
        assertEquals(true, firstNum.isEqual(secondNum));
    }

    @Test
    void testIsEqualFalse() {
        BigInt firstNum = new BigInt("123");
        BigInt secondNum = new BigInt("5555");
        assertEquals(false, firstNum.isEqual(secondNum));
    }

    @Test
    void testIsEqualInt() {
        BigInt firstNum = new BigInt("123");
        assertEquals(false, firstNum.isEqual(5));
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void Print() {
        BigInt a = new BigInt("12345");
        a.print();
        assertEquals("12345", outputStream.toString().trim());
        outputStream.reset();
        a.setVariableValue("54321");
        a.print();
        assertEquals("54321", outputStream.toString().trim());
    }

}