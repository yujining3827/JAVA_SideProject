package scheduleManagement;

public class CustomExceptions extends Exception {
	
	//용량 가득 찼을 때 발생하는 Exception
    public static class ScheduleOverflowException extends Exception {
        public ScheduleOverflowException(String message) {
            super(message);
        }
    }

    // 다른 예외 메시지 출력 가능
    public static class CustomException extends Exception {
    	 public CustomException(String message) {
             super(message);
         }
    }

}
