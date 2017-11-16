
public class EmailText {
    private final static String emailText = "test text";

    public static String getEmailText(){
        return emailText;
    }

    public static Boolean checkEmailText(String text){
        boolean checkResult;
        if (text.equals(emailText)) checkResult = true;
        else checkResult = false;
        return checkResult;
    }
}
