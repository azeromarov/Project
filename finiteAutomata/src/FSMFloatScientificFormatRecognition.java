public class FSMFloatScientificFormatRecognition {
    public static boolean automatafloats(String input){
        String digits = new String("0123456789");
        enum State{
            initial,
            firstState,
            secondState,
            thirdState,
            fourthState, fifthState, errorState
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
                if(input.charAt(i) == '.') {
                    state = State.secondState;
                } else if(digits.indexOf(input.charAt(i)) != -1) {
                    state = State.firstState;
                } else {
                    state = State.errorState;
                }
            } else if(state == State.secondState){
                if(digits.indexOf(input.charAt(i)) != -1){
                    state = State.thirdState;
                } else{
                    state = State.errorState;
                }
            } else if(state == State.thirdState){
                if(input.charAt(i) == 'e' || input.charAt(i) == 'E'){
                    state = State.fourthState;
                } else if(digits.indexOf(input.charAt(i)) != -1){
                    state = State.thirdState;
                } else {
                    state = State.errorState;
                }
            } else if(state == State.fourthState){
                if(input.charAt(i) == '+' || input.charAt(i) == '-' || digits.indexOf(input.charAt(i)) != -1){
                    state = State.fifthState;
                } else {
                    state = State.errorState;
                }
            } else if(state == State.fifthState){
                if(digits.indexOf(input.charAt(i)) != -1){
                    state = State.fifthState;
                } else {
                    state = State.errorState;
                }
            }
        }

        if(state == State.firstState || state == State.thirdState || state == State.fifthState){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args){
        String s = new String("-4564.456e-46");
        System.out.println(automatafloats(s));
    }
}
