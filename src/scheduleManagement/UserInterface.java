
package scheduleManagement;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.GregorianCalendar;


public class UserInterface {

	static void schedule() {
		System.out.println("");
		System.out.println("*********일정 관리 프로그램*********");
		System.out.println("1. 일정 추가");
		System.out.println("2. 중요도 변경 ");
		System.out.println("3. 카테고리 변경 ");
		System.out.println("4. 일정 검색 ");
		System.out.println("5. 일정 삭제");
		System.out.println("6. 일정 수정");
		System.out.println("7. 일정 메모");
		System.out.println("8. 전체 일정 확인");
		System.out.println("9. 종료");
		System.out.println("--------------------------------");
		System.out.println("원하시는 프로그램 번호를 입력해주세요 : ");
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Management manager = new Management();
		
		try {
            while (true) {
                schedule(); // 메뉴를 보여준다
                int choice = 0;
                
                try {
                    choice = scan.nextInt(); 
                    scan.nextLine(); // 입력 버퍼 비우기
                } catch (InputMismatchException e) {
                    System.out.println("잘못입력하셨습니다. 1부터 9까지의 숫자를 입력하세요.");
                    scan.nextLine(); // 입력 버퍼 비우기
                    continue; // 다음 반복으로 넘어가기
                }
                
                try {
                   if(choice >=1 && choice<=9) {
                	   if (manager.getSize() == 0 && choice != 1 && choice != 8 && choice != 9) 
                        throw new CustomExceptions.CustomException("일정을 먼저 추가해주세요.");
                	   if (manager.getSize() == 0 && choice == 8) 
                           throw new CustomExceptions.CustomException("등록된 일정이 없습니다. 일정을 먼저 추가해주세요.");
                   }
                   
                }catch (CustomExceptions.CustomException e) {
                	   // 예외 처리
                	   System.out.println(e.getMessage());
                       scan.nextLine(); // 입력 버퍼 비우기
                       continue;
                	}


                switch (choice) {
                    case 1: //일정 추가 
                    	while(true) {
                    	
                    		try {
                    			System.out.println("--------------------------------");
                    			System.out.println("일정을 입력하세요 (제목, 시작연도, 시작월, 시작일, 시작시간, 종료연도, 종료월, 종료일, 종료시간 ) : \n");
                    			String input = scan.nextLine();
                    			
                    			String[] tokens = input.split(" ");
                    			if (tokens.length != 9) {
                                    throw new IllegalArgumentException("입력값이 부족합니다. 다시 입력해주세요.");
                                }

                    			String title = tokens[0]; // 제목
                    			
                    			int year1 = Integer.parseInt(tokens[1]); // 연도
                    			int month1 = Integer.parseInt(tokens[2]); // 월
                    			int date1 = Integer.parseInt(tokens[3]); // 일
                    			
                    			String time1 = tokens[4]; //시작 시간
                    			String[] startTimeTokens = time1.split(":");
                    			int startHour = Integer.parseInt(startTimeTokens[0]);
                    			int startMinute = Integer.parseInt(startTimeTokens[1]);	
	                        
		                        int year2 = Integer.parseInt(tokens[5]); // 연도
		                        int month2 = Integer.parseInt(tokens[6]); // 월
		                        int date2 = Integer.parseInt(tokens[7]); // 일
		                        
		                        String time2 = tokens[8]; //종료 시간
		                        String[] endTimeTokens = time2.split(":");
		                        int endHour = Integer.parseInt(endTimeTokens[0]);
		                        int endMinute = Integer.parseInt(endTimeTokens[1]);
		                        
		                     
		                        // GregorianCalendar 객체 생성
		                        GregorianCalendar startDate = new GregorianCalendar(year1, month1, date1, startHour, startMinute);
		                        GregorianCalendar endDate = new GregorianCalendar(year2, month2, date2, endHour, endMinute);
		                        
		                     
		                        // 일정이 겹치는지 확인
		                        boolean isExist = false;
		                        boolean validResponse = false; // 올바른 입력 여부를 추적하기 위한 변수
		                        
		                        for (int i = 0; i < manager.getSize(); i++) {
			                      Schedule existingSchedule = manager.getSchedule(i);
			                      if (existingSchedule != null && existingSchedule.getTitle().equals(title)) {
				                     // 겹치는 일정이 있는 경우
				                     System.out.println("이미 같은 제목의 일정이 있습니다.");
				                     System.out.println("해당 일정을 추가하시겠습니까? (y/n)");
				                                
					                 while(!validResponse) {
					                     String response = scan.nextLine();
				                         if (response.equalsIgnoreCase("y")) {
				                        	 // 추가할 경우
				                        	 isExist = true;
				                             validResponse = true;
				                             break;
				                        } else if (response.equalsIgnoreCase("n")) {
				                        	// 추가하지 않을 경우
				                            validResponse = true;
				                            System.out.println("추가하지 않았습니다. ");
				                            break;
				                        } else {// 잘못된 입력 처리
				                                    System.out.println("잘못된 입력입니다. (y/n)로 입력해주세요.");
				                                	
				                        }          
					                 }
			                      }
			                      if (validResponse && !isExist) {
			                          break; // validResponse가 true이고 isExist가 false이면 반복문 탈출
			                      }        
			                   }
		                        
		                        
		                        
		                        if (isExist || !validResponse) {
			                        // 카테고리 입력
			                        char category = ' ';
			                        
			                        while (category == ' ') {
			                        	try {
			                            
			                        		System.out.println("------카테고리를 입력하세요----- ");
			                        		System.out.println( "(1번 : SCHOOL, 2번 : APPOINTMENT, 3번 : NOTIFICATION, 4번 : OTHER) ");
			                        		
			                        		int inputCategory = scan.nextInt();
			                        		scan.nextLine(); // 버퍼 비우기
			                        		switch (inputCategory) {
			                                case 1:
			                                    category = 'S';
			                                    break;
			                                case 2:
			                                    category = 'A';
			                                    break;
			                                case 3:
			                                    category = 'N';
			                                    break;
			                                case 4:
			                                    category = 'O';
			                                    break;
			                                default:
			                                    System.out.println("유효한 카테고리를 입력하세요.");
			                        		}
			                            } catch (InputMismatchException e) {
			                                System.out.println("숫자를 입력하세요.");
			                                scan.nextLine(); // 잘못된 입력을 비우기
			                                continue;
			                            }
			                        }
			                        
			                        // Schedule 객체 생성 시 카테고리도 함께 전달
			                        Schedule newSchedule = new Schedule(title, startDate, endDate, category);
			                        try {
			                            manager.addSchedule(newSchedule);
			                            System.out.println("일정을 추가했습니다. ");
			                            break;
			                        } catch (CustomExceptions.ScheduleOverflowException e) {
			                            System.out.println(e.getMessage());
			                        }
			                        break;
		                        }
		                    
                    		}catch (IllegalArgumentException e) {
		                        System.out.println(" *** (enter누른 후) 올바른 형식으로 입력해주세요 !! ***");
		                        System.out.println("예) 2024년 5월 20일 12시30분 ~ 2024년 5월 25일 13시 스터디 일정 이라면 ");
		                        System.out.println("       < 스터디 2024 5 20 12:30 2024 5 25 13:00 > 로 입력해주세요. ");
		                        
		                    }catch (ArrayIndexOutOfBoundsException e ) {
		                    	System.out.println("입력값이 부족합니다. 다시 입력해주세요. ");
		                    }
		                    
                    	}
                    		
                    	break;
                    	
                    case 2: //중요도 설정 및 변경 
                    	System.out.println("===== 중요도 설정 =====");
                    	
                    	int end2 = manager.getSize();

					    for (int i=0; i< end2; i++) {
					    	Schedule s = manager.getSchedule(i); 
                            System.out.println("일정: " + s.getTitle() + 
                            		", 일시 : " + s.getScheduleDate() + 
                            		", 중요도 : " + s.getPriority() + 
                            		", 카테고리 : " + s.getCategoryName());
                        }
                    	
                    	while(true) {
                    	try {
                    		System.out.println("--------------------------------");
                        	System.out.println("중요도 부여하고 싶은 일정을 고르세요 : ");
                        	//scan.nextLine(); // 입력 버퍼 비우기
                        	int num = scan.nextInt();
                        	
                        	if (num < 1 || num > manager.getSize()) {
                                throw new CustomExceptions.CustomException("존재하지 않는 일정입니다. 다시 입력해주세요. ");
                            }
                        	
                        	System.out.println("설정할 중요도를 입력하세요: ");
                        	System.out.print("1(:가장 중요한 일)부터 5까지의 수 ");
                        	
                        	//scan.nextLine(); // 입력 버퍼 비우기
                            int priority = scan.nextInt();
                            System.out.print(manager.setPriority(num - 1, priority));
                           break;
                        }catch (CustomExceptions.CustomException e) {
                            System.out.println(e.getMessage());
                            
                        }catch (InputMismatchException e) {
                            System.out.println("중요도는 1부터 5까지의 숫자 중에서 입력해야합니다.");
                            
                        }
                       
                    	}
                        
                        break;
                        
                        
                
                    case 3: //카테고리 변경 
                    	
                    	System.out.println("===== 카테고리 변경 =====");
                    	
                    	int i=0;
                    	int end = manager.getSize();
                    	
                    	for (i=0; i< end; i++) {
					    	Schedule s = manager.getSchedule(i); 
                            System.out.println("일정: " + s.getTitle() + 
                            		", 일시 : " + s.getScheduleDate() + 
                            		", 중요도 : " + s.getPriority() + 
                            		", 카테고리 : " + s.getCategoryName());
                        }
                    	
                    	while(true) {
                    	try {
                    		System.out.println("--------------------------------");
                        	System.out.println("카테고리를 변경하고 싶은 일정을 고르세요 : ");
                        	//scan.nextLine(); // 입력 버퍼 비우기
                        	int num = scan.nextInt();
                        	
                        	if (num < 1 || num > manager.getSize()) {
                                throw new CustomExceptions.CustomException("존재하지 않는 일정입니다. 다시 입력해주세요. ");
                            }
                        	
                        	char category = ' ';
	                        
	                        while (category == ' ') {
	                        	try {
	                            
	                        		System.out.println("변경하고자하는 카테고리를 입력하세요: ");
	                            	System.out.print("(1번 : SCHOOL, 2번 : APPOINTMENT, 3번 : NOTIFICATION, 4번 : OTHER)");
	                            	
	                        		int inputCategory = scan.nextInt();
	                        		scan.nextLine(); // 버퍼 비우기
	                        		switch (inputCategory) {
	                                case 1:
	                                    category = 'S';
	                                    break;
	                                case 2:
	                                    category = 'A';
	                                    break;
	                                case 3:
	                                    category = 'N';
	                                    break;
	                                case 4:
	                                    category = 'O';
	                                    break;
	                                default:
	                                    System.out.println("유효한 카테고리를 입력하세요.");
	                        		}

	                                System.out.print(manager.setCategory(num-1 , category));
	                            } catch (InputMismatchException e) {
	                                System.out.println("숫자를 입력하세요.");
	                                scan.nextLine(); // 잘못된 입력을 비우기
	                                continue;
	                            }
	                        }
	                        
                        }catch (CustomExceptions.CustomException e) {
                            System.out.println(e.getMessage());
                            
                        }catch (InputMismatchException e) {
                            System.out.println("카테고리는 1-4번 중에서 입력해야합니다.");
                            
                        }
                    	break;
                       
                    	}
                        
                        break;
                    	
                    	
                    case 4: // 일정 검색
                    case 5: //일정 삭제 
                    case 6: //일정 수정 
                    	
                    	if(choice==5) {//삭제시 추가되는 부분 
                        	System.out.println("<삭제를 위해 우선 검색을 하겠습니다.>");
                        }
                    
                    	if( choice ==6 ) { //수정시 추가되는 부분 
                    		System.out.println("<수정을 위해 우선 검색을 하겠습니다.>");
                    	}	
                			
                		while(true) {
                			    System.out.println("========일정을 검색합니다.========");
                			    System.out.println("1) 날짜 검색");
                			    System.out.println("2) 날짜 사이 검색");
                			    System.out.println("3) 일정 내용 검색");
                			    System.out.println("4) 중요도로 검색");
                			    System.out.println("5) 카테고리로 검색");
                			    System.out.println("검색할 조건을 선택하세요: ");
                			    
                				int searchOption = 0; 
                					
                				try {
                					searchOption = scan.nextInt();  
                					scan.nextLine(); // 개행문자 처리
                						
                				}catch (InputMismatchException e) {
                				    System.out.println("잘못 입력하셨습니다. 1부터5사이의 숫자를 입력하세요.");
                				    scan.nextLine(); // 입력 버퍼 비우기
                				    continue; // 다음 반복으로 넘어가기
                				}
                				
                				int[] searchResultIndexes = null;

                				switch (searchOption) {
                				
                					 case 1:
                					    while(true) {	
                					    	try{
                					    		System.out.println("-------------------------------------");
                					    		System.out.println("검색할 년도를 입려해주세요. :");
                					    		String yearInput = scan.nextLine().trim();
                					    		System.out.println("검색할 달을 입력해주세요. (생략하고 싶으시면 enter를 눌러주세요.):");
                					    		String monthInput = scan.nextLine().trim();
                					    		System.out.println("검색할 일을 입력해주세요.(생략하고 싶으시면 enter를 눌러주세요.):");
                					    		String dayInput = scan.nextLine().trim();
                					    		
                						

                					    		// year, month, day의 기본값을 현재 날짜로 설정
                					    		int searchYear = yearInput.isEmpty() ? -1 : Integer.parseInt(yearInput);
                					    		int searchMonth = monthInput.isEmpty() ? -1 :  Integer.parseInt(monthInput);
                					    		int searchDay = dayInput.isEmpty() ? -1 : Integer.parseInt(dayInput);
                					    		
                					    		GregorianCalendar searchDate = new GregorianCalendar(searchYear, searchMonth, searchDay);
                							
                					    		searchResultIndexes = manager.searchIndexes(searchDate);
                					    		
                					    		// 검색된 일정들의 인덱스 배열을 출력
            							        if (searchResultIndexes.length == 0) {
            							            System.out.println("검색된 결과가 없습니다.");
            							        } else {
            							            for (i = 0; i < searchResultIndexes.length; i++) {
            							                int index = searchResultIndexes[i];
            							                Schedule schedule = manager.getSchedule(index);
            							                System.out.println(i+1 + "번) 일정 제목: " + schedule.getTitle() + 
            				                            		", 일시 : " + schedule.getScheduleDate() + 
            				                            		", 중요도 : " + schedule.getPriority() + 
            				                            		", 카테고리 : " + schedule.getCategoryName());
            							            }
            							        }
            							        
                					    		break;
                					    	
                					    	}catch (NumberFormatException e) {
                					    		System.out.println("날짜를 제대로 입력해주세요. ");
                					    		System.out.println("날짜는 공백이나 숫자만 가능합니다. ");
                					    	}
                					    }
                					    break;
                					    	
                					    	
	
                					 case 2:
                						 while(true) {
                					    	try {
                					    		// 날짜 사이 검색
                					    		System.out.println("검색할 시작 날짜를 입력하세요 (년 월 일): ");
                					    		int startYear = scan.nextInt();
                					    		int startMonth = scan.nextInt();
                					    		int startDay = scan.nextInt();
                					    		scan.nextLine();
                					    		
                					    		System.out.println("검색할 종료 날짜를 입력하세요 (년 월 일): ");
                					    		int endYear = scan.nextInt();
                					    		int endMonth = scan.nextInt();
                					    		int endDay = scan.nextInt();
                					    		scan.nextLine();
                					    		
                					    		GregorianCalendar searchStartDate = new GregorianCalendar(startYear, startMonth, startDay);
                					    		GregorianCalendar searchEndDate = new GregorianCalendar(endYear, endMonth, endDay);
                					    		
                					    		searchResultIndexes = manager.searchIndexes(searchStartDate, searchEndDate);

                					    		// 검색된 일정들의 인덱스 배열을 출력
            							        if (searchResultIndexes.length == 0) {
            							            System.out.println("검색된 결과가 없습니다.");
            							        } else {
            							            for (i = 0; i < searchResultIndexes.length; i++) {
            							                int index = searchResultIndexes[i];
            							                Schedule schedule = manager.getSchedule(index);
            							                System.out.println(i+1 + "번) 일정 제목: " + schedule.getTitle() + 
            				                            		", 일시 : " + schedule.getScheduleDate() + 
            				                            		", 중요도 : " + schedule.getPriority() + 
            				                            		", 카테고리 : " + schedule.getCategoryName());
            							            }
            							        }
            							        
                					    		break;
                					    		
                					    	}catch(InputMismatchException e){
                					    		System.out.println("유효한 날짜를 입력하세요.");
                					    		scan.nextLine();
                					    	}
                					    }
                					    	
                					    	break;

                					 case 3:
                					     // 일정 내용 검색
                					     System.out.println("검색할 일정 내용을 입력하세요: ");
                					     String searchString = scan.nextLine();
                					     searchResultIndexes = manager.searchIndexes(searchString);
                				
                					     // 검색된 일정들의 인덱스 배열을 출력
     							        if (searchResultIndexes.length == 0) {
     							            System.out.println("검색된 결과가 없습니다.");
     							        } else {
     							            for (i = 0; i < searchResultIndexes.length; i++) {
     							                int index = searchResultIndexes[i];
     							                Schedule schedule = manager.getSchedule(index);
     							                System.out.println(i+1 + "번) 일정 제목: " + schedule.getTitle() + 
   				                            		", 일시 : " + schedule.getScheduleDate() + 
   				                            		", 중요도 : " + schedule.getPriority() + 
   				                            		", 카테고리 : " + schedule.getCategoryName());
     							            }
     							        }
                				    		
                					     break;

                					 case 4:
                						// 중요도로 검색
                						 while (true) {
                							 try {
                							        System.out.println("=== 검색할 중요도를 입력하세요 ===");
                							        int priority = scan.nextInt();
                							        scan.nextLine();

                							        if (priority < 1 || priority > 5) {
                							            System.out.println("중요도는 1부터 5까지의 숫자입니다.");
                							            System.out.println("다시 입력해주세요.");
                							            continue;
                							        }

                							        searchResultIndexes = manager.searchIndexes(priority);

                							        // 검색된 일정들의 인덱스 배열을 출력
                							        if (searchResultIndexes.length == 0) {
                							            System.out.println("검색된 결과가 없습니다.");
                							        } else {
                							            for (i = 0; i < searchResultIndexes.length; i++) {
                							                int index = searchResultIndexes[i];
                							                Schedule schedule = manager.getSchedule(index);
                							                System.out.println(i+1 + "번) 일정 제목: " + schedule.getTitle() + 
                				                            		", 일시 : " + schedule.getScheduleDate() + 
                				                            		", 중요도 : " + schedule.getPriority() + 
                				                            		", 카테고리 : " + schedule.getCategoryName());
                							            }
                							        }
                							        
                							        break;

                							    } catch (InputMismatchException e) {
                							        System.out.println("유효한 숫자를 입력하세요.");
                							        scan.nextLine(); // 입력 버퍼 비우기
                							    }   
                							}
                						 	break;

                					 case 5:
                						 //카테고리로 검색 
                						 while(true) {
                					    	try {
                					    		System.out.println("-----------------------------------");
                					    		System.out.println("검색할 카테고리 번호를 입력하세요 (1번 : SCHOOL, 2번 : APPOINTMENT, 3번 : NOTIFICATION, 4번 : OTHER): ");
                					    		int searchCategoryInput = scan.nextInt();
                					    		scan.nextLine(); // 버퍼 비우기

                					    		char searchCategory = ' ';
                					    		switch (searchCategoryInput) {
                					    			case 1:		
                					    				searchCategory = 'S';
                					    				break;
                					    			case 2:
                					    				searchCategory = 'A';
                					    				break;
                					    			case 3:
                					    				searchCategory = 'N';
                					    				break;
                					    			case 4:
                					    				searchCategory = 'O';
                					                	break;
                					    			default:
                					    				System.out.println("유효한 카테고리를 입력하세요.");
                					    				continue;
                					    		}
                					    	

                					    		searchResultIndexes = manager.searchIndexes(searchCategory);
                					        
                					     
                					        // 검색된 일정들의 인덱스 배열을 출력
        							        if (searchResultIndexes.length == 0) {
        							            System.out.println("검색된 결과가 없습니다.");
        							        } else {
        							            for (i = 0; i < searchResultIndexes.length; i++) {
        							                int index = searchResultIndexes[i];
        							                Schedule schedule = manager.getSchedule(index);
        							                System.out.println(i+1 + "번) 일정 제목: " + schedule.getTitle() + 
        				                            		", 일시 : " + schedule.getScheduleDate() + 
        				                            		", 중요도 : " + schedule.getPriority() + 
        				                            		", 카테고리 : " + schedule.getCategoryName());
        							            }
        							        }
                				    		break;
                					        
                					    	}catch(InputMismatchException e) {
                			                    System.out.println("유효한 숫자를 입력하세요.");
                			                    scan.nextLine(); // 입력 버퍼 비우기
                			                }
                						 }
                						 
                						 break;
                			                
                					    default:
                					        System.out.println("잘못된 입력입니다.");
                					        System.out.println("1부터 5중에 골라주세요.");
                					        continue;    
                					}
	                			
                				//case 5에 실행되어야할 부분 
                				if (choice == 5) {
	                					try {
	                					if(searchResultIndexes.length == 0) {
	                						throw new CustomExceptions.CustomException("삭제를 종료하겠습니다. ");
	                					}else if(searchResultIndexes.length == 1) {
	                						//하나일땐 어떻게 할거임?... 해당 일정을 삭제할거냐 물어보고 
	                						System.out.println("해당 일정을 삭제하시겠습니까? (y/n)");
	                						 boolean validResponse = false;
	                						 
	                						while(!validResponse) {
				                                String response = scan.nextLine();
				                                if (response.equalsIgnoreCase("y")) {// 삭제할 경우, 삭제진행 
				                                	manager.delete(searchResultIndexes[0]);
				    	                            System.out.println("일정이 삭제되었습니다.");
				                                	 validResponse = true;
				                                    break;
				                                } else if (response.equalsIgnoreCase("n")) {// 삭제하지 않을 경우, 삭제 종료 
				                                	validResponse = true;
				                                    System.out.println("삭제하지 않고, 종료하겠습니다. ");
				                                    break;
				                                } else {// 잘못된 입력 처리
				                                    System.out.println("잘못된 입력입니다. (y/n)로 입력해주세요.");
				                                	
				                                }
				                               
				                            }
	                						
	                					}else {
	                						
		    	                            System.out.println("삭제할 일정의 번호를 입력하세요: ");
		    	                            System.out.println("(만약, 삭제할 일정이 없다면 100을 입력해주세요.) ");
		    	                            int deleteNum = scan.nextInt(); // -> 선택삭제 하기 위한 코드 
		    	                            scan.nextLine(); // 버퍼 비우기
		    	                            
		    	                            if(deleteNum==100) {
		    	                            	throw new CustomExceptions.CustomException("삭제를 종료했습니다. ");
		    	                            }
	                						
		    	                            int deleteIndex = searchResultIndexes[deleteNum-1];
		    	                            manager.delete(deleteIndex);
		    	                            System.out.println("일정이 삭제되었습니다.");
	                					}//else 
		    	                            
	                					}catch (CustomExceptions.CustomException e) {
	                						System.out.println(e.getMessage());
	                						break;
		    	                        } catch (IndexOutOfBoundsException e) {
		    	                            System.out.println(e.getMessage());
		    	                        }
	                					
	    	                        } //if(choice ==5) 

                					//case 6에 실행되어야할 부분 
	                				if(choice==6) {
	                					try {
		                					if(searchResultIndexes.length == 0) {
		                						throw new CustomExceptions.CustomException("수정을 종료하겠습니다. ");
		                					
		                					}else { //검색결과가 여러개인 경우 
		                						
		                						System.out.println("--------------------------");
			    	                            System.out.println("수정할 일정의 번호를 입력하세요: ");
			    	                            System.out.println("(만약, 삭제할 일정이 없다면 100을 입력해주세요.) ");
			    	                            int editNum = scan.nextInt(); // -> 선택수정 하기 위한 코드 
			    	                            scan.nextLine(); // 버퍼 비우기
			    	                            
			    	                            if(editNum==100) {
			    	                            	throw new CustomExceptions.CustomException("수정을 종료했습니다. ");
			    	                            }
		                						
			    	                            int editIndex = searchResultIndexes[editNum];
			    	                            
			    	                            Schedule schedule = manager.getSchedule(editIndex);
			    	                            System.out.println("선택된 일정은 \n 일정 제목: " + schedule.getTitle() + 
				                            		", 일시 : " + schedule.getScheduleDate() + 
				                            		", 중요도 : " + schedule.getPriority() + 
				                            		", 카테고리 : " + schedule.getCategoryName());
			    	                            
			    	                            System.out.println("");
			    	                            
			    	                            //String editInput = scan.nextLine();
			    	                            Schedule editSchedule = manager.getSchedule(editIndex);
			    	                            
			    	                            System.out.println("수정할 일정 제목을 넣어주세요. (변경하지 않으려면 엔터 키를 누르세요): ");
			    	                            String editTitle = scan.nextLine().trim();
			    	                            String newTitle = editTitle.isEmpty() ? newTitle = editSchedule.getTitle() : editTitle.trim();
			                        			
			    	                            System.out.println("수정할 일정 시작일자를 넣어주세요. (변경하지 않으려면 enter를 눌러주세요.) : ");
			    	                            GregorianCalendar newStartDate;
			    	                            try {
			    	                                String editStartDate = scan.nextLine().trim();
			    	                                if (editStartDate.isEmpty()) {
			    	                                    newStartDate = editSchedule.getStartDate();
			    	                                } else {
			    	                                    String[] editStartDateTokens = editStartDate.split(" ");
			    	                                    int editStartYear = Integer.parseInt(editStartDateTokens[0]);
			    	                                    int editStartMonth = Integer.parseInt(editStartDateTokens[1]) ; 
			    	                                    int editStartDay = Integer.parseInt(editStartDateTokens[2]);
			    	                                    
			    	                                    System.out.println("수정할 일정 시작시간을 넣어주세요. (변경하지 않으려면 enter를 눌러주세요.) : ");
					    	                            String editStartTime = scan.nextLine().trim();
			    	                                    String[] timeTokens = editStartTime.split(":");
			    	                                    int editStarHour = Integer.parseInt(timeTokens[0]);
			    	                                    int editStartMinute = Integer.parseInt(timeTokens[1]);
			    	                                    
			    	                                    newStartDate = new GregorianCalendar(editStartYear, editStartMonth, editStartDay, editStarHour, editStartMinute );
			    	                                }
			    	                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			    	                                newStartDate = editSchedule.getStartDate();
			    	                            }
			    	                            
			    	                            
			    	                            System.out.println("수정할 일정 종료일자를 넣어주세요. (변경하지 않으려면 enter를 눌러주세요.) : ");
			    	                            GregorianCalendar newEndDate ;
			    	                            try {
			    	                            	String editEndDate = scan.nextLine().trim();
			    	                            	if (editEndDate.isEmpty()) {
			    	                            		newEndDate = editSchedule.getStartDate();
			    	                                } else {
			    	                                	String[] editEndDateTokens = editEndDate.split(" ");
					    	                            int  editEndYear = Integer.parseInt(editEndDateTokens[0]);
						    	                        int  editEndMonth = Integer.parseInt(editEndDateTokens[1]);
						    	                        int  editEndDay = Integer.parseInt(editEndDateTokens[2]);
		                					    		
						    	                        System.out.println("수정할 일정 종료시간을 넣어주세요. (변경하지 않으려면 enter를 눌러주세요.) : ");
						    	                        String editEndTime = scan.nextLine().trim();
						    	                        String[] timeTokens = editEndTime .split(":");
			    	                                    int editEndHour = Integer.parseInt(timeTokens[0]);
			    	                                    int editEndMinute = Integer.parseInt(timeTokens[1]);
			    	                                    
			    	                                    newEndDate = new GregorianCalendar(editEndYear, editEndMonth, editEndDay, editEndHour, editEndMinute );
			    	                                }
			    	                            	
			    	                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			    	                                newEndDate = editSchedule.getEndDate();
			    	                            }
			    	                            
			    	                            char newCategory = schedule.getCategory();
			    	                            
			    	                            //같은 인덱스 번호에 수정 내용 덮어 씌우
			    	                            Schedule editNewSchedule = new Schedule(newTitle, newStartDate, newEndDate, newCategory);
			    	                            manager.update(editIndex, editNewSchedule); 
			    	                            System.out.println("일정이 수정되었습니다.");
			    	                            
		                					}//else 
			    	                            
		                					}catch (CustomExceptions.CustomException e) {
		                						System.out.println(e.getMessage());
		                						break;
			    	                        } catch (IndexOutOfBoundsException e) {
			    	                            System.out.println(e.getMessage());
			    	                        }
                						
                					}//if(choice ==6) 닫음 
	                				
	                				break;
	                				
                				} //while (true) 닫음 
                
                				break;
                
                    case 7: 
                    	//일정 메모
                    	break;
                	
                
                    case 8: //전체 일정 확인 
                    	System.out.println("전체 일정을 확인합니다:");
                    
                    	int end3 = manager.getSize();

					    for (i=0; i< end3; i++) {
					    	Schedule s = manager.getSchedule(i); 
                            System.out.println("일정: " + s.getTitle() + 
                            		", 일시 : " + s.getScheduleDate() + 
                            		", 중요도 : " + s.getPriority() + 
                            		", 카테고리 : " + s.getCategoryName());
                        }
                    	
                        break;
                    	
                    case 9: // 프로그램 종료
                        System.out.println("프로그램을 종료합니다.");
                        return; 
                        
                    default:
                        System.out.println("잘못된 메뉴 번호입니다. 다시 입력하세요.");
                        break;
                        
                } //switch (choice) 닫힘 
            
            }
		}finally {
                    scan.close(); 
                } // Scanner 객체 닫기
               
    }//main닫기  

	
} //UserInterface클래스 닫기  




