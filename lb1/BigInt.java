package by.lupach.bigint.core;

public class BigInt {
    private String variable;

    public String getVariableValue() {
        return variable;
    }

    public void setVariableValue(String variable) {
        this.variable = variable;
    }


    public BigInt(String variable) {
        this.variable = variable;
        this.deleteZeroAtFirstPosition();
    }

    public BigInt() {
        this.variable = "0";
    }

    private void deleteZeroAtFirstPosition(){
        if (this.variable.length() < 2){
            return;
        }

        boolean isNegative = false;
        StringBuilder result;

        if (this.variable.charAt(0) == '-'){
         this.deleteMinus();
         isNegative = true;
        }

        result = new StringBuilder(this.variable);
        while(result.charAt(0) == '0' && result.length() > 1){
            result.deleteCharAt(0);
        }
        this.variable = result.toString();

        if (isNegative){
            this.insertMinus();
        }
    }

    private void deleteMinus() {
        StringBuilder unsignedNum = new StringBuilder(this.variable);
        unsignedNum.deleteCharAt(0);
        this.variable = unsignedNum.toString();
    }

    private void insertMinus(){
        StringBuilder negativeNum = new StringBuilder(this.variable);
        negativeNum.insert(0, '-');
        this.variable = negativeNum.toString();
    }

    private void insetZeroesAtEnd(int count){
        if (count <= 0){
            return;
        }

        this.variable = this.variable + "0".repeat(count);
    }

    private BigInt sumOfNegatives(BigInt secondNum) {
        StringBuilder unsignedNum;
        if(this.variable.charAt(0) == '-'){
            unsignedNum = new StringBuilder(this.variable);
        }else {
            unsignedNum = new StringBuilder(secondNum.variable);
        }
        unsignedNum.deleteCharAt(0);
        BigInt unsignedNumBigInt = new BigInt(unsignedNum.toString());
        if (this.variable.charAt(0) == '-') {
            return secondNum.difference(unsignedNumBigInt);
        } else {
            if (this.isBigger(unsignedNumBigInt)) {
                this.variable = this.difference(unsignedNumBigInt).variable;
                return new BigInt(this.variable);
            }

            this.variable = unsignedNumBigInt.difference(this).variable;
            this.insertMinus();
            return new BigInt(this.variable);
        }
    }

    public BigInt sum(BigInt secondNum) {
        if (this.variable.charAt(0) == '-' || secondNum.variable.charAt(0) == '-') {
            return this.sumOfNegatives(secondNum);
        }

        StringBuilder result = new StringBuilder();

        int dozen = 0;
        int countOfDigitsInFirstNum = this.variable.length() - 1;
        int countOfDigitsInSecondNum = secondNum.variable.length() - 1;

        for (int i = countOfDigitsInFirstNum, j = countOfDigitsInSecondNum,
             lastDigitFirstNum,
             lastDigitSecondNum;
             i >= 0 || j >= 0; i--, j--) {
            lastDigitFirstNum = (i >= 0) ? this.variable.charAt(i) - '0' : 0;
            lastDigitSecondNum = (j >= 0) ? secondNum.variable.charAt(j) - '0' : 0;
            int resultLastDigit = (lastDigitFirstNum + lastDigitSecondNum + dozen) % 10;
            dozen = (lastDigitFirstNum + lastDigitSecondNum + dozen > 9) ? 1 : 0;
            result.insert(0, resultLastDigit);
        }

        if (dozen != 0) {
            result.insert(0, dozen);
        }

        this.variable = result.toString();
        return new BigInt(result.toString());
    }


    public BigInt sum(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return this.sum(secondNumWithOverriddenType);
    }


    public void increment() {
        this.variable = this.sum(1).variable;
    }

    private BigInt differenceDecreasingNumIsSmaller(BigInt subtractedNum) {
        BigInt result = subtractedNum.difference(this);
        result.insertMinus();
        return new BigInt(result.variable);
    }

    private BigInt differenceOfNegatives(BigInt subtractedNum){
        if (this.variable.charAt(0) == '-' && subtractedNum.variable.charAt(0) == '-') {
            this.deleteMinus();
            subtractedNum.deleteMinus();
            return subtractedNum.difference(this);
        }

        if (subtractedNum.variable.charAt(0) == '-') {
            subtractedNum.deleteMinus();
            return this.sum(subtractedNum);
        }else{
            this.deleteMinus();
            this.sum(subtractedNum);
            this.insertMinus();
            return new BigInt(this.variable);
        }
    }

    private String differenceGetResult(BigInt subtractedNum, int countOfDigitsInFirstNum, int countOfDigitsInSecondNum) {
        StringBuilder result = new StringBuilder();
        for (int i = countOfDigitsInFirstNum, j = countOfDigitsInSecondNum, dozen = 0, lastDigitFirstNum,
             lastDigitSecondNum, resultLastDigit; i >= 0 || j >= 0; i--, j--) {
            lastDigitFirstNum = (i >= 0) ? this.variable.charAt(i) - '0' : 0;
            lastDigitSecondNum = (j >= 0) ? subtractedNum.variable.charAt(j) - '0' : 0;
            if(lastDigitFirstNum - dozen >= lastDigitSecondNum){
                resultLastDigit =  lastDigitFirstNum - dozen - lastDigitSecondNum;
            }
            else{
                resultLastDigit = lastDigitFirstNum + 10 - lastDigitSecondNum - dozen;
            }
            dozen = (lastDigitFirstNum - dozen < lastDigitSecondNum || (lastDigitFirstNum == 0) && dozen != 0) ? 1 : 0;

            result.insert(0, resultLastDigit);
        }
        return result.toString();
    }

    public BigInt difference(BigInt subtractedNum) {
        if (this.variable.charAt(0) == '-' || subtractedNum.variable.charAt(0) == '-') {
            return this.differenceOfNegatives(subtractedNum);
        }

        if (this.isSmaller(subtractedNum)) {
            return this.differenceDecreasingNumIsSmaller(subtractedNum);
        }

        int countOfDigitsInFirstNum = this.variable.length() - 1;
        int countOfDigitsInSecondNum = subtractedNum.variable.length() - 1;

        this.variable = differenceGetResult(subtractedNum, countOfDigitsInFirstNum, countOfDigitsInSecondNum);
        this.deleteZeroAtFirstPosition();
        return new BigInt(this.variable);
    }

    public BigInt difference(int subtractedNum) {
        BigInt subtractedNumWithOverriddenType = new BigInt(String.valueOf(subtractedNum));
        return this.difference(subtractedNumWithOverriddenType);
    }

    public void decrement() {
        this.variable = this.difference(1).variable;
    }


    private Boolean isBiggerCompareNumsWithIdenticalLength(BigInt secondNum, boolean firstNumNegative){
        int countOfDigitsInNums = this.variable.length();
        for (int i = 0, firstDigitThisNum, firstDigitSecondNum; i < countOfDigitsInNums; i++) {
            firstDigitThisNum = this.variable.charAt(i) - '0';
            firstDigitSecondNum = secondNum.variable.charAt(i) - '0';
            if (firstDigitThisNum > firstDigitSecondNum && firstNumNegative) {
                return false;
            }
            if (firstDigitThisNum > firstDigitSecondNum) {
                return true;
            }
            if (firstDigitThisNum < firstDigitSecondNum && firstNumNegative) {
                return true;
            }
            if (firstDigitThisNum < firstDigitSecondNum) {
                return false;
            }
        }
        return false;
    }

    public Boolean isBigger(BigInt secondNum) {
        this.deleteZeroAtFirstPosition();       //deleting 0 at first position e.g. :0123 => 123
        secondNum.deleteZeroAtFirstPosition();  //deleting 0 at first position e.g. :0123 => 123

        boolean firstNumNegative = this.variable.charAt(0) == '-';
        boolean secondNumNegative = secondNum.variable.charAt(0) == '-';

        if (firstNumNegative && !secondNumNegative) {
            return false;
        }

        if (!firstNumNegative && secondNumNegative) {
            return true;
        }

        if (this.variable.length() > secondNum.variable.length()){
            return true;
        }

        if (this.variable.length() < secondNum.variable.length()) {
            return false;
        }

        return this.isBiggerCompareNumsWithIdenticalLength(secondNum, firstNumNegative);
    }

    public Boolean isBigger(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return isBigger(secondNumWithOverriddenType);
    }

    private Boolean isSmallerCompareNumsWithIdenticalLength(BigInt secondNum, boolean firstNumNegative){
        int countOfDigitsInNums = this.variable.length() - 1;
        for (int i = 0, firstDigitThisNum, firstDigitSecondNum; i <= countOfDigitsInNums; i++) {
            firstDigitThisNum = this.variable.charAt(i) - '0';
            firstDigitSecondNum = secondNum.variable.charAt(i) - '0';
            if (firstDigitThisNum < firstDigitSecondNum && firstNumNegative) {
                return false;
            }
            if (firstDigitThisNum < firstDigitSecondNum) {
                return true;
            }
            if (firstDigitThisNum > firstDigitSecondNum && firstNumNegative) {
                return true;
            }
            if (firstDigitThisNum > firstDigitSecondNum) {
                return false;
            }
        }
        return false;
    }

    public Boolean isSmaller(BigInt secondNum) {
        this.deleteZeroAtFirstPosition();       //deleting 0 at first position e.g. :0123 => 123
        secondNum.deleteZeroAtFirstPosition();  //deleting 0 at first position e.g. :0123 => 123

        boolean firstNumNegative = this.variable.charAt(0) == '-';
        boolean secondNumNegative = secondNum.variable.charAt(0) == '-';

        if (firstNumNegative && !secondNumNegative) {
            return true;
        }

        if (!firstNumNegative && secondNumNegative){
            return false;
        }


        if (this.variable.length() > secondNum.variable.length()) {
            return false;
        }

        if (this.variable.length() < secondNum.variable.length()) {
            return true;
        }

        return this.isSmallerCompareNumsWithIdenticalLength(secondNum, firstNumNegative);
    }

    public Boolean isSmaller(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return isSmaller(secondNumWithOverriddenType);
    }

    public Boolean isEqual(BigInt secondNum) {
        return !this.isBigger(secondNum) && !this.isSmaller(secondNum);
    }

    public Boolean isEqual(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return isEqual(secondNumWithOverriddenType);
    }

    private boolean multiplyNegativeNums(BigInt secondNum){
        if (this.variable.charAt(0) == '-' && secondNum.variable.charAt(0) == '-') {
            this.deleteMinus();
            secondNum.deleteMinus();
            return false;
        }

        if (this.variable.charAt(0) == '-') {
            this.deleteMinus();
            return true;
        }

        if (secondNum.variable.charAt(0) == '-') {
            secondNum.deleteMinus();
            return true;
        }

        return false;
    }

    private BigInt multiplyByDigit(int digit){
        StringBuilder summand = new StringBuilder();
        int countOfDigitsInFirstNum = this.variable.length();
        int dozen = 0;

        for (int j = countOfDigitsInFirstNum - 1, lastDigitFirstNum; j >= 0; j--) {
            lastDigitFirstNum = this.variable.charAt(j) - '0';
            int resultLastDigit = (lastDigitFirstNum * digit + dozen) % 10;
            if(lastDigitFirstNum * digit + dozen > 9){
                dozen = (lastDigitFirstNum * digit + dozen) / 10;
            }
            else{
                dozen = 0;
            }
            summand.insert(0, resultLastDigit);
        }

        if (dozen != 0){
            summand.insert(0, dozen);
        }

        return new BigInt(summand.toString());
    }

    public BigInt multiply(BigInt secondNum) {
        boolean isResultNegativeNum =  this.multiplyNegativeNums(secondNum);

        int countOfDigitsInFirstNum = this.variable.length();
        int countOfDigitsInSecondNum = secondNum.variable.length();

        if (countOfDigitsInSecondNum > countOfDigitsInFirstNum) {
            return secondNum.multiply(this);
        }

        BigInt result = new BigInt();

        for (int i = countOfDigitsInSecondNum - 1, lastDigitSecondNum; i >= 0; i--) {
            lastDigitSecondNum = secondNum.variable.charAt(i) - '0';
            BigInt partialResult = this.multiplyByDigit(lastDigitSecondNum);
            partialResult.insetZeroesAtEnd(countOfDigitsInSecondNum - i - 1);
            result.sum(partialResult);
        }

        if (isResultNegativeNum) {
            result.insertMinus();
        }

        this.variable = result.variable;
        return result;
    }

    public BigInt multiply(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return this.multiply(secondNumWithOverriddenType);
    }

    private boolean divisionNegativeNums(BigInt divisor){
        if (this.variable.charAt(0) == '-' && divisor.variable.charAt(0) == '-') {
            this.deleteMinus();
            divisor.deleteMinus();
            return false;
        }

        if (this.variable.charAt(0) == '-') {
            this.deleteMinus();
            return true;
        }

        if (divisor.variable.charAt(0) == '-') {
            divisor.deleteMinus();
            return true;
        }


        return false;
    }

    private String divisionFindNumThatCanBeDivided(BigInt remainder, int digitIndex){
        BigInt numToDivide = new BigInt();
        if (!remainder.isEqual(0)){
            numToDivide.variable = remainder.multiply(10).variable;
            numToDivide.sum(new BigInt(String.valueOf(this.variable.charAt(digitIndex))));
        }else{
            numToDivide.variable = String.valueOf(this.variable.charAt(digitIndex));
        }
        return numToDivide.variable;
    }

    private int divisionFindPartialResult(BigInt numToDivide, BigInt divisor, BigInt remainder){
        int digit = 0;
        while (numToDivide.isBigger(divisor) || numToDivide.isEqual(divisor)) {
            numToDivide = numToDivide.difference(divisor);
            digit++;
        }
        remainder.variable = numToDivide.variable;
        return digit;
    }

    public BigInt division(BigInt divisor) {
        if (divisor.variable.equals("0")) {
            throw new ArithmeticException("Division by zero");
        }

        boolean isResultNegativeNum = divisionNegativeNums(divisor);

        if (divisor.isBigger(this)) {
            return new BigInt("0");
        }

        BigInt remainder = new BigInt();
        StringBuilder result = new StringBuilder();
        BigInt numToDivide = new BigInt();

        for (int i = 0; i < this.variable.length(); i++) {
            numToDivide.variable = divisionFindNumThatCanBeDivided(remainder, i);
            while (numToDivide.isSmaller(divisor) && i + 1 < this.variable.length()) {
                numToDivide = numToDivide.multiply(10);
                numToDivide.sum(new BigInt(divisionFindNumThatCanBeDivided(new BigInt(), i++)));
                result.append('0');
            }
            result.append(divisionFindPartialResult(numToDivide, divisor, remainder));
        }

        this.variable = result.toString();
        if (isResultNegativeNum) {
            this.insertMinus();
        }

        this.deleteZeroAtFirstPosition();       //previously added 0 for adding every new element to numToDivide, when numToDivide was smaller, than divisor
        return new BigInt(this.variable);
    }

    public BigInt division(int divisor) {
        BigInt divisorWithOverriddenType = new BigInt(String.valueOf(divisor));
        return this.division(divisorWithOverriddenType);
    }

    public static int toInt(BigInt num) {
        return Integer.parseInt(num.variable);
    }

    public void print() {
        System.out.println(this.variable);
    }

}
