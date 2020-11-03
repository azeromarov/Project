public class FSMIdentifierRecognition {
    public static boolean automataid(String input){
        String digits = new String("0123456789");
        enum State{
            initial,
            firstState,
            errorState
        }
        State state = State.initial;

        for(int i=0;i<input.length();i++){
            if(state == State.initial){
                if(Character.isLetter(input.charAt(i)) == true){
                    state = State.firstState;
                }
                else{
                    state = State.errorState;
                }
            } else if(state == State.firstState){
                if(Character.isLetter((input.charAt(i))) == true || digits.indexOf(input.charAt(i)) != -1 || input.charAt(i) == '_'){
                    state = State.firstState;
                } else {
                    state = State.errorState;
                }
            }
        }

        if(state == State.firstState){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args){
        String s = new String("sdf4_");
        System.out.println(automataid(s));
    }
}
