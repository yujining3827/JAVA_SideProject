
package scheduleManagement;

import java.util.Scanner;
import java.util.GregorianCalendar;


public class Management {
	
	private Schedule[] schedules; // 일정 배열
    private int size; // 현재 일정 개수
    
    Scanner scan = new Scanner(System.in);
    
    //배열 생성 및 기본일정 생성 
    public Management() {
        this.schedules = new Schedule[100]; // 최대 100개의 일정을 저장할 수 있는 배열 생성
        this.size = 0;
        
        try {
            // 기본 일정 추가
            addSchedule(new Schedule("자바과제", 
                new GregorianCalendar(2024, 4, 20, 12, 0), 
                new GregorianCalendar(2024, 4, 22, 12, 0), 'S'));
            addSchedule(new Schedule("스터디", 
                new GregorianCalendar(2024, 5, 10, 12, 0), 
                new GregorianCalendar(2024, 5, 10, 13, 0), 'A'));
            addSchedule(new Schedule("점심약속", 
                new GregorianCalendar(2024, 5, 1, 12, 0), 
                new GregorianCalendar(2024, 5, 1, 13, 0), 'N'));
            addSchedule(new Schedule("4학년", 
                new GregorianCalendar(2025, 2, 1, 9, 0), 
                new GregorianCalendar(2025, 2, 1, 10, 0), 'O'));
        } catch (CustomExceptions.ScheduleOverflowException e) {
            System.out.println(e.getMessage());
        }
    }

    // 일정 추가 메서드
    public void addSchedule(Schedule newSchedule) throws CustomExceptions.ScheduleOverflowException {
        if (size < schedules.length) {
            schedules[size++] = newSchedule;
        } else {
            throw new CustomExceptions.ScheduleOverflowException("더 이상 일정을 추가할 수 없습니다.");
        }
    }
    
 
    // 특정 인덱스의 일정 제목 반환 메서드
    public String getTitle(int index) {
        if (index >= 0 && index < size) {
            return schedules[index].getTitle();
        } else {
            return null; // index가 올바르지 않을 경우 null 반환 또는 예외 처리
        }
    }
    
    // 일정의 중요도를 설정하는 메서드
    public String setPriority(int index, int priority) throws CustomExceptions.CustomException {
        if (index >= 0 && index < schedules.length) {
            if (schedules[index] == null) {
                throw new CustomExceptions.CustomException("해당 일정이 존재하지 않습니다.");
            }
            if (priority >= 1 && priority <= 5) {
                schedules[index].setPriority(priority);
                return schedules[index].getTitle() + "의 중요도: " + priority;
            } else {
                throw new CustomExceptions.CustomException("중요도를 잘못 입력하셨습니다 .");
            }
        } else {
            throw new CustomExceptions.CustomException("유효하지 않은 인덱스입니다.");
        }
    }
 
 
    public String setCategory(int index, char category) throws CustomExceptions.CustomException {
    	
    	if (index >= 0 && index < schedules.length) {
            if (schedules[index] == null) {
                throw new CustomExceptions.CustomException("해당 일정이 존재하지 않습니다.");
            }
            if (category == 'S' || category == 'A'|| category == 'N' || category == 'O') {
            	 schedules[index].setCategory(category);
                 return schedules[index].getTitle() + "의 카테고리 : " + category;
            } else {
                throw new CustomExceptions.CustomException("중요도를 잘못 입력하셨습니다 .");
            }
        } else {
            throw new CustomExceptions.CustomException("유효하지 않은 인덱스입니다.");
        }
    	
    }
 
    // 해당 날짜의 이후 일정을 검색
    public int[] searchIndexes(GregorianCalendar date) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            GregorianCalendar scheduleStartDate = schedules[i].getStartDate();
            GregorianCalendar scheduleEndDate = schedules[i].getEndDate();
            if (!scheduleStartDate.before(date)) {
            	// scheduleDate.before(date)는 scheduleDate가 date보다 이전인지 확인하는 조건
                count++;
            }else if(!scheduleEndDate.before(date)) {
            	count++;
            }
        }

        int[] indexes = new int[count];
        int resultCount = 0;

        for (int i = 0; i < size; i++) {
            GregorianCalendar scheduleStartDate = schedules[i].getStartDate();
            GregorianCalendar scheduleEndDate = schedules[i].getEndDate();
            if (!scheduleStartDate.before(date)) {
                indexes[resultCount++] = i;
            }else if(!scheduleEndDate.before(date)) {
            	indexes[resultCount++] = i;
            }
        }

        return indexes;
    }

    // 해당 날짜 사이의 일정들을 검색하는 메서드
    public int[] searchIndexes(GregorianCalendar start, GregorianCalendar end) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            GregorianCalendar startDate = schedules[i].getStartDate();
            GregorianCalendar endDate = schedules[i].getEndDate();
            if (startDate.compareTo(end) <= 0 && endDate.compareTo(start) >= 0) {
            	// 시작 날짜(startDate)가 종료 날짜(end)와 같거나 이전인지 확인
            	// 종료 날짜(endDate)가 시작 날짜(start)와 같거나 이후인지 확인
                count++;
            }
        }

        int[] indexes = new int[count];
        int resultCount = 0;

        for (int i = 0; i < size; i++) {
            GregorianCalendar startDate = schedules[i].getStartDate();
            GregorianCalendar endDate = schedules[i].getEndDate();
            if (startDate.compareTo(end) <= 0 && endDate.compareTo(start) >= 0) {
                indexes[resultCount++] = i;
            }
        }

        return indexes;
    }

    
    // 해당 문자열이 일정 제목에 포함되어 있는 일정들을 검색하는 메서드
    public int[] searchIndexes(String searchString) {
    	// 우선순위가 일치하는 일정의 개수를 먼저 센다
        int count = 0;
        for (int i = 0; i < size; i++) {
        	// 현재 일정의 제목 가져오기
            String title = schedules[i].getTitle();
            if (title != null && title.toLowerCase().contains(searchString.toLowerCase())) {
                count++;
            }
        }
    	
    	// 결과를 저장할 배열 생성
    	int[] indexes = new int[count];
    	int resultCount = 0;

        // 일정을 반복하여 검색
        for (int i = 0; i < size; i++) {
            // 현재 일정의 제목 가져오기
            String title = schedules[i].getTitle();
            if (title != null && title.toLowerCase().contains(searchString.toLowerCase())) { 
                // 해당 조건에 해당하는 일정이라면 결과 배열에 추가
            	indexes[resultCount++] = i;
            }
        }
        // 결과 배열 반환
        return indexes;
    }
    
    
    public int[] searchIndexes(int priority) {
        // 우선순위가 일치하는 일정의 개수를 먼저 센다
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (schedules[i].getPriority() == priority) {
                count++;
            }
        }

        // 일치하는 일정의 개수만큼의 배열을 생성
        int[] indexes = new int[count];
        int resultCount = 0;

        // 일정을 반복하여 검색하여 일치하는 일정의 인덱스를 배열에 추가
        for (int i = 0; i < size; i++) {
            int currentPriority = schedules[i].getPriority();
            if (currentPriority == priority) {
                indexes[resultCount++] = i;
            }
        }

        // 결과 배열 반환
        return indexes;
    }
    
    // 해당 카테고리의 일정들을 검색하는 메서드
    public int[] searchIndexes(char category) {
    	// 우선순위가 일치하는 일정의 개수를 먼저 센다
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (schedules[i].getCategory() == category) {
                count++;
            }
        }
    	
        // 일치하는 일정의 개수만큼의 배열을 생성
    	int[] indexes = new int[count];
    	int resultCount = 0;

        // 일정을 반복하여 검색
        for (int i = 0; i < size; i++) {
            // 현재 일정의 카테고리 가져오기
            char currentCategory = schedules[i].getCategory();

            // 현재 일정의 카테고리가 입력된 카테고리와 일치하는지 확인
            if (currentCategory == category) {
                // 해당 카테고리의 일정이라면 결과 배열에 추가
            	indexes[resultCount++] = i;  
            }
        }

     // 결과 배열 반환
        return indexes;
    }


    // 제목이 같으면 같은 객체라고 인지하고 이에 대한 index를 리턴해 주는 함수 
    public int search(Schedule s) {
        for (int i = 0; i < size; i++) {
            if (s.equals(schedules[i])) {
                return i; // 동일한 일정을 찾으면 해당 인덱스 반환
            }
        }
        return -1; // 일치하는 일정을 찾지 못한 경우 -1 반환
    }
    
    
 
   // 일정 삭제 메서드
   public void delete(int index) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("유효하지 않은 인덱스: " + index);
	    }
	    for (int i = index; i < size - 1; i++) {
	        schedules[i] = schedules[i + 1];
	    }
	    schedules[size - 1] = null; // 마지막 요소를 null로 설정
	    size--;
	}
  
   // 일정 수정 메서드
   public void update(int index, Schedule s) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("유효하지 않은 인덱스: " + index);
	    }
	    // Assuming schedules is the list or array holding the schedules
	    schedules[index] = s;
	}
    
  
   //전체일정 출력에서 사용 
  public int getSize() {
      return size;
  }
  

  //인덱스에 해당하는 일정을 반환하는 메서드
  public Schedule getSchedule(int index) {
      if (index >= 0 && index < size) {
          return schedules[index];
      } else {
          return null; // 예외 처리: 인덱스가 올바르지 않은 경우
      }
  }
  
  
  //모든 일정을 배열로 반환하는 메서드
  public Schedule[] getSchedules() {
      return schedules;
  }

  
  
  


	
}
