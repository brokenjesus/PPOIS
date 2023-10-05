package by.lupach.autorent.core;

public class Money{
    private int value; //cents

    Money(int value){
        this.value = value;
    }

    public Money(String floatString){
        getCentsValueOfFloatString(floatString);
    }

    public int getValue(){
        return value;
    }

    private void getCentsValueOfFloatString(String floatString){
        StringBuilder result = new StringBuilder(floatString);

        try {
            if (result.charAt(result.length()-1) == '.'){   //100.
                result.deleteCharAt(result.length()-1);
                value = Integer.parseInt(result.toString())*100;
                return;
            }
            if (result.charAt(result.length()-2) == '.'){   //100.1
                result.deleteCharAt(result.length()-2);
                value = Integer.parseInt(result.toString())*10;
                return;
            }
            if (result.charAt(result.length()-3) == '.'){   //100.11
                result.deleteCharAt(result.length()-3);
                value = Integer.parseInt(result.toString());
                return;
            }
        }catch (Exception e){
            value = Integer.parseInt(result.toString())*100;
        }
    }

    public String getDollarValueOfCents(){
        StringBuilder valueInDollars = new StringBuilder(String.valueOf(value));
        if(valueInDollars.length() == 2){
            valueInDollars.insert(0, "0.");
        }else if(valueInDollars.length() == 1){
            valueInDollars.insert(0, "0.0");
        }else{
            valueInDollars.insert(valueInDollars.length()-2, ".");
        }
        return valueInDollars.toString();
    }

}
