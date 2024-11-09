
package scheduleManagement;
import java.util.GregorianCalendar;


public class Schedule {
	
	private String title;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private int priority;
	private char category;
	
	// 생성자
	public Schedule(String title, GregorianCalendar startDate, GregorianCalendar endDate, char category) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = 5;
		this.category = category;
	}
	
	// 제목 가져오는 매서
	public String getTitle() {
	        return title; // index가 올바르지 않을 경우 null 반환 또는 예외 처리
	}

	 // date 필드의 getter 메서드
	 public String getScheduleDate() {
		    return startDate.get(GregorianCalendar.YEAR) + "년 " + 
		           startDate.get(GregorianCalendar.MONTH) + "월 " + 
		           startDate.get(GregorianCalendar.DAY_OF_MONTH) + "일" +
		           startDate.get(GregorianCalendar.HOUR_OF_DAY) + ":" + 
	               startDate.get(GregorianCalendar.MINUTE) + "~" + 
	               endDate.get(GregorianCalendar.YEAR) + "년 " + 
	               endDate.get(GregorianCalendar.MONTH) + "월 " + 
	               endDate.get(GregorianCalendar.DAY_OF_MONTH) + "일" +
		           endDate.get(GregorianCalendar.HOUR_OF_DAY) + ":" + 
		           endDate.get(GregorianCalendar.MINUTE);


		}

	 
	 
	// 시작 날짜 getter 메서드
	public GregorianCalendar getStartDate() {
	    return startDate;
	}
	
	// 종료 날짜 getter 메서드
	public GregorianCalendar getEndDate(){
	    return endDate;
	}
	
	
	// 시작 시간 getter 메서드
	public String getStartTime() {
		return startDate.get(GregorianCalendar.HOUR_OF_DAY) + ":" + 
		               startDate.get(GregorianCalendar.MINUTE) ;
		}
	
	
	//종료 시간 getter 메서드
	public String getEndTime() {
		return startDate.get(GregorianCalendar.HOUR_OF_DAY) + ":" + 
					startDate.get(GregorianCalendar.MINUTE) ;
			}
	
	 // priority 필드의 getter 메서드
    public int getPriority() {
        return priority;
    }

 
    // 카테고리 이름을 가져오는 메서드
    public String getCategoryName() {
        switch (category) {
            case 'S': return "SCHOOL";
            case 'A': return "APPOINTMENT";
            case 'N': return "NOTIFICATION";
            case 'O': return "OTHER";
            default: return "UNKNOWN";
        }
    }
    	
    // category 필드의 getter 메서드
    public char getCategory() {
    	return category;
    }
    
    
    // category 설정 메서드 
    public void setCategory(char category) {
        this.category = category;
    }

    // 중요도 설정 메서드
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
 
    
    
    //equals 매서드 
    public boolean equals(Object obj) {
		//자기자신과 비교
		if (this == obj) 
			return true;
		//null이거나 클래스 타입이 다를땐 false
		if (obj == null || !(obj instanceof Schedule)) 
			return false;
		Schedule schedule = (Schedule) obj; //Schedule 타입으로 캐스트
		return title.equals(schedule.title) ;
	}

}
