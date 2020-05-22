package LambdaYoutube.lambdaProbi;
interface ComputeNum{
    public boolean compute(int n);
}

interface MyString{
    String myStringFunc(String str);
}
public class Proba1 {
    public static void main(String[] args) {
        ComputeNum paren = (n) -> n%2 == 0;
        ComputeNum dvocifren = (n) -> n/100 < 0;
        ComputeNum isNegative = (n) -> n < 0;

        System.out.println(paren.compute(6));


        System.out.println("");
        System.out.println("");
        System.out.println("");


        MyString reverse =  (str) ->{
            String res = "" ;
            for(int i = str.length(); i > 0; i--){
                res+= str.charAt(i - 1);
            }
            return res;
        };
        System.out.println(reverse.myStringFunc("Diler"));
    }
}
