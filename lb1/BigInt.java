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

        result = new StringBuilder(this.variable);
        if (this.variable.charAt(0) == '-'){
            result.deleteCharAt(0);
            isNegative = true;
        }

        while(result.charAt(0) == '0' && result.length() > 1){
            result.deleteCharAt(0);
        }

        if (isNegative){
            result.insert(0, '-');
        }
        this.variable = result.toString();
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
                this.setVariableValue(this.difference(unsignedNumBigInt).variable);
                return this;
            }
            StringBuilder result = new StringBuilder(unsignedNumBigInt.difference(this).variable);
            result.insert(0, '-');
            this.setVariableValue(result.toString());
            return this;
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

        this.setVariableValue(result.toString());
        return new BigInt(result.toString());
    }


    public BigInt sum(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return this.sum(secondNumWithOverriddenType);
    }


    public void increment() {
        this.variable = this.sum(1).variable;
    }


    private BigInt differenceTwoNegatives(BigInt subtractedNum) {
        StringBuilder num1 = new StringBuilder(this.variable);
        num1.deleteCharAt(0);
        this.variable = num1.toString();

        StringBuilder num2 = new StringBuilder(subtractedNum.variable);
        num2.deleteCharAt(0);
        subtractedNum.variable = num2.toString();

        return subtractedNum.difference(this);
    }

    private BigInt differenceSubtractedNumIsNegative(BigInt subtractedNum) {
        StringBuilder num = new StringBuilder(subtractedNum.variable);
        num.deleteCharAt(0);
        BigInt unsignedSecondNum = new BigInt(num.toString());
        return this.sum(unsignedSecondNum);
    }

    private BigInt differenceDecreasingNumIsNegative(BigInt subtractedNum) {
        StringBuilder num = new StringBuilder(this.variable);
        num.deleteCharAt(0);
        BigInt unsignedFirstNum = new BigInt(num.toString());
        BigInt resultBigInt = unsignedFirstNum.sum(subtractedNum);
        StringBuilder result = new StringBuilder(resultBigInt.variable);
        result.insert(0, '-');
        resultBigInt.variable = result.toString();
        return resultBigInt;
    }

    private BigInt differenceDecreasingNumIsSmaller(BigInt subtractedNum) {
        BigInt resultBigIntType = subtractedNum.difference(this);
        StringBuilder unsignedResult = new StringBuilder(resultBigIntType.variable);
        unsignedResult.insert(0, "-");
        resultBigIntType.variable = unsignedResult.toString();
        return resultBigIntType;
    }

    public BigInt difference(BigInt subtractedNum) {
        if (this.variable.charAt(0) == '-' && subtractedNum.variable.charAt(0) == '-') {
            return this.differenceTwoNegatives(subtractedNum);
        }

        if (subtractedNum.variable.charAt(0) == '-') {
            return this.differenceSubtractedNumIsNegative(subtractedNum);
        }

        if (this.variable.charAt(0) == '-') {
            return this.differenceDecreasingNumIsNegative(subtractedNum);
        }

        StringBuilder result = new StringBuilder();

        if (this.isEqual(subtractedNum)) {
            return new BigInt("0");
        }

        if (this.isSmaller(subtractedNum)) {
            return this.differenceDecreasingNumIsSmaller(subtractedNum);
        }

        int dozen = 0;
        int countOfDigitsInFirstNum = this.variable.length() - 1;
        int countOfDigitsInSecondNum = subtractedNum.variable.length() - 1;
        for (int i = countOfDigitsInFirstNum, j = countOfDigitsInSecondNum, lastDigitFirstNum, lastDigitSecondNum;
             i >= 0 || j >= 0; i--, j--) {
            lastDigitFirstNum = (i >= 0) ? this.variable.charAt(i) - '0' : 0;
            lastDigitSecondNum = (j >= 0) ? subtractedNum.variable.charAt(j) - '0' : 0;
            int resultLastDigit;
            if(lastDigitFirstNum - dozen >= lastDigitSecondNum){
                resultLastDigit =  lastDigitFirstNum - dozen - lastDigitSecondNum;
            }
            else{
                resultLastDigit = lastDigitFirstNum + 10 - lastDigitSecondNum - dozen;
            }
            dozen = (lastDigitFirstNum - dozen < lastDigitSecondNum || (lastDigitFirstNum == 0) && dozen != 0) ? 1 : 0;

            result.insert(0, resultLastDigit);
        }

        while (result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }

        this.setVariableValue(result.toString());
        return new BigInt(result.toString());
    }

    public BigInt difference(int subtractedNum) {
        BigInt subtractedNumWithOverriddenType = new BigInt(String.valueOf(subtractedNum));
        return this.difference(subtractedNumWithOverriddenType);
    }

    public void decrement() {
        this.variable = this.difference(1).variable;
    }

    private void isBiggerBothNegative(BigInt secondNum) {
        StringBuilder unsignedFirstNum = new StringBuilder(this.variable);
        unsignedFirstNum.deleteCharAt(0);
        this.variable = unsignedFirstNum.toString();

        unsignedFirstNum = new StringBuilder(secondNum.variable);
        unsignedFirstNum.deleteCharAt(0);
        secondNum.variable = unsignedFirstNum.toString();
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

        if (firstNumNegative) {             //if first negative second is negative too, else returned in previous if
            this.isBiggerBothNegative(secondNum);
        }


        if (this.variable.length() > secondNum.variable.length()){
            return true;
        }

        if (this.variable.length() < secondNum.variable.length()) {
            return false;
        }

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

    public Boolean isBigger(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return isBigger(secondNumWithOverriddenType);
    }

    private void isSmallerBothNumNegative(BigInt secondNum) {
        StringBuilder unsignedFirstNum = new StringBuilder(this.variable);
        unsignedFirstNum.deleteCharAt(0);
        this.variable = unsignedFirstNum.toString();

        unsignedFirstNum = new StringBuilder(secondNum.variable);
        unsignedFirstNum.deleteCharAt(0);
        secondNum.variable = unsignedFirstNum.toString();
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

        if (firstNumNegative) {                 //if first negative second is negative too, else returned in previous if
            this.isSmallerBothNumNegative(secondNum);
        }

        if (this.variable.length() > secondNum.variable.length()) {
            return false;
        }

        if (this.variable.length() < secondNum.variable.length()) {
            return true;
        }

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

    private void multiplyTwoNumsAreNegative(BigInt secondNum) {
        StringBuilder unsignedFirstNum = new StringBuilder(this.variable);
        StringBuilder unsignedSecondNum = new StringBuilder(secondNum.variable);
        unsignedFirstNum.deleteCharAt(0);
        unsignedSecondNum.deleteCharAt(0);
        this.variable = unsignedFirstNum.toString();
        secondNum.variable = unsignedSecondNum.toString();
    }

    private void multiplyFirstNumIsNegative() {
        StringBuilder unsignedFirstNum = new StringBuilder(this.variable);
        unsignedFirstNum.deleteCharAt(0);
        this.variable = unsignedFirstNum.toString();
    }

    private void multiplySecondNumIsNegative(BigInt secondNum) {
        StringBuilder unsignedFirstNum = new StringBuilder(secondNum.variable);
        unsignedFirstNum.deleteCharAt(0);
        secondNum.variable = unsignedFirstNum.toString();
    }

    public BigInt multiply(BigInt secondNum) {
        boolean isResultNegativeNum = false;

        if (this.variable.charAt(0) == '-' && secondNum.variable.charAt(0) == '-') {
            this.multiplyTwoNumsAreNegative(secondNum);
        }

        if (this.variable.charAt(0) == '-') {
            this.multiplyFirstNumIsNegative();
            isResultNegativeNum = true;
        }

        if (secondNum.variable.charAt(0) == '-') {
            this.multiplySecondNumIsNegative(secondNum);
            isResultNegativeNum = true;
        }

        int countOfDigitsInFirstNum = this.variable.length();
        int countOfDigitsInSecondNum = secondNum.variable.length();

        if (countOfDigitsInSecondNum > countOfDigitsInFirstNum) {
            return secondNum.multiply(this);
        }

        StringBuilder summand = new StringBuilder();

        BigInt result = new BigInt();

        int dozen = 0;

        for (int i = countOfDigitsInSecondNum - 1, lastDigitSecondNum; i >= 0; i--) {
            lastDigitSecondNum = secondNum.variable.charAt(i) - '0';
            for (int j = countOfDigitsInFirstNum - 1, lastDigitFirstNum; j >= 0; j--) {
                lastDigitFirstNum = this.variable.charAt(j) - '0';
                int resultLastDigit = (lastDigitFirstNum * lastDigitSecondNum + dozen) % 10;
                if(lastDigitFirstNum * lastDigitSecondNum + dozen > 9){
                    dozen = (lastDigitFirstNum * lastDigitSecondNum + dozen) / 10;
                }
                else{
                    dozen = 0;
                }
                summand.insert(0, resultLastDigit);
            }
            for (int k = countOfDigitsInSecondNum - 1; k > i; k--) {
                summand.insert(summand.length(), "0");
            }
            result.sum(new BigInt(summand.toString()));
            summand.delete(0, summand.length());
            dozen = (i == 0) ? dozen : 0;
        }

        if (dozen != 0) {
            StringBuilder unsignedFinalResult = new StringBuilder(result.variable);
            unsignedFinalResult.insert(0, dozen);
            result.variable = unsignedFinalResult.toString();
        }


        if (isResultNegativeNum) {
            StringBuilder signedFinalResult = new StringBuilder(result.variable);
            signedFinalResult.insert(0, '-');
            result.variable = signedFinalResult.toString();
        }

        this.setVariableValue(result.toString());
        return result;
    }

    public BigInt multiply(int secondNum) {
        BigInt secondNumWithOverriddenType = new BigInt(String.valueOf(secondNum));
        return this.multiply(secondNumWithOverriddenType);
    }

    private void divisionTwoNumsAreNegative(BigInt divisor) {
        StringBuilder unsignedFirstNum = new StringBuilder(this.variable);
        StringBuilder unsignedSecondNum = new StringBuilder(divisor.variable);
        unsignedFirstNum.deleteCharAt(0);
        unsignedSecondNum.deleteCharAt(0);
        this.variable = unsignedFirstNum.toString();
        divisor.variable = unsignedSecondNum.toString();
    }

    private void divisionFirstNumIsNegative() {
        StringBuilder unsignedFirstNum = new StringBuilder(this.variable);
        unsignedFirstNum.deleteCharAt(0);
        this.variable = unsignedFirstNum.toString();
    }

    private void divisionSecondNumIsNegative(BigInt divisor) {
        StringBuilder unsignedSecondNum = new StringBuilder(divisor.variable);
        unsignedSecondNum.deleteCharAt(0);
        divisor.variable = unsignedSecondNum.toString();
    }

    public BigInt division(BigInt divisor) {
        if (divisor.variable.equals("0")) {
            throw new ArithmeticException("Division by zero");
        }

        boolean isResultNegativeNum = false;

        if (this.variable.charAt(0) == '-' && divisor.variable.charAt(0) == '-') {
            this.divisionTwoNumsAreNegative(divisor);
        }

        if (this.variable.charAt(0) == '-') {
            this.divisionFirstNumIsNegative();
            isResultNegativeNum = true;
        }

        if (divisor.variable.charAt(0) == '-') {
            this.divisionSecondNumIsNegative(divisor);
            isResultNegativeNum = true;
        }

        if (divisor.isBigger(this)) {
            return new BigInt("0");
        }

        BigInt remainder = new BigInt();
        StringBuilder semiResult = new StringBuilder();
        BigInt numToDivide = new BigInt();
        StringBuilder numThatCanProbablyBeDivided;

        for (int i = 0, j = 0; i < this.variable.length(); i++) {
            numToDivide.variable = String.valueOf(this.variable.charAt(i));
            numThatCanProbablyBeDivided = new StringBuilder(numToDivide.variable);
            if (!remainder.isEqual(0)){
                int remainderIntType = toInt(remainder);
                int numThatCanProbablyBeDividedIntType = toInt(new BigInt(numThatCanProbablyBeDivided.toString()));
                numToDivide.variable = String.valueOf(remainderIntType * 10 + numThatCanProbablyBeDividedIntType);
            }
            while (numToDivide.isSmaller(divisor) && i + 1 < this.variable.length()) {
                numThatCanProbablyBeDivided.insert(numThatCanProbablyBeDivided.length(), this.variable.charAt(i + 1));  //adding next element of dividend
                semiResult.insert(semiResult.length(), "0");
                numToDivide.variable = numThatCanProbablyBeDivided.toString();
                i++;

            }

            while (numToDivide.isBigger(divisor) || numToDivide.isEqual(divisor)) {
                numToDivide = numToDivide.difference(divisor);
                j++;
            }
            remainder.variable = numToDivide.variable;
            semiResult.insert(semiResult.length(), j);
            j = 0;
        }

        if (isResultNegativeNum) {
            semiResult.insert(0, '-');
        }

        this.setVariableValue(semiResult.toString());
        this.deleteZeroAtFirstPosition();       //previously added 0 for adding every new element to numThatProbablyCanBeDivided, when numThatProbablyCanBeDivided was smaller, than divisor
        return new BigInt(this.getVariableValue());
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
