public class DataForTst {
    private static String email = "testemailepam@inbox.ru";
    private static String pass = "123pass";
    private static String toEmail = "testemailepam@inbox.ru";
    private static String subject = "testemail";
    private static String dateLastDraft;

    public static String getEmail(){
        return email;
    }

    public static String getPass(){
        return pass;
    }

    public static String getToEmail(){
        return toEmail;
    }

    public static String getSubject(){
        return subject;
    }

    public static String getDateLastDraft(){
        return dateLastDraft;
    }

    public static void setDateLastDraft(String date){
        dateLastDraft =  date;
    }
}
