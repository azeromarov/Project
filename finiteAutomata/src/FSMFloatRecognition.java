public class FSMFloatRecognition {

    public static boolean automatafloat(String input){
        String digits = new String("0123456789");
        enum State{
            initial,
            firstState,
            secondState,
            errorState
        }
        State state = State.initial;

        for(int i=0;i<input.length();i++){
            if(state == State.initial){
                if(input.charAt(i) == '+' || input.charAt(i) == '-' || digits.indexOf(input.charAt(i)) != -1){
                    state = State.firstState;
                } else {
                    state = State.errorState;
                }
            } else if(state == State.firstState){
                if(input.charAt(i) == '.'){
                    state = State.secondState;
                } else if(digits.indexOf(input.charAt(i)) != -1){
                    state = State.firstState;
                } else{
                    state = State.errorState;
                }
            } else if(state == State.secondState){
                if(digits.indexOf(input.charAt(i)) != -1){
                    state = State.secondState;
                } else {
                    state = State.errorState;
                }
            }
        }

        if(state == State.firstState || state == State.secondState){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args){
        String s = new String("-1.2");
        System.out.println(automatafloat(s));
    }
}
