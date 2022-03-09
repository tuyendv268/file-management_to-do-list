package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Task;

public class TaskDao {
	private InputStreamReader reader;
	private OutputStreamWriter writer;
	private Integer ID;
	private String taskname;
	private LocalDate startDate;
	private LocalDate endDate;
	private String address;
	private Boolean status;
	
	public TaskDao() {
		
	}
	

	public boolean saveTask(Task task, File dataFile) {
		try {
			FileOutputStream file = new FileOutputStream(dataFile, true);
			writer = new OutputStreamWriter(file, StandardCharsets.UTF_8);
			String text = task.getId().toString() + "_" + task.getTaskname()+"_"
						+task.getStartDate()+"_"+task.getEndDate()+"_"+task.getAddress()+"_"+task.getStatus()+"\n";
			try {
				writer.write(text);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Task findTask(String name, File dataFile)  {
		String data = "";
		Task task = null;
		int c;
		
		try {
			FileInputStream file = new FileInputStream(dataFile);
			reader = new InputStreamReader(file, StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					if(c == (int)'\n') {
						data = data.substring(0, data.length()-1);
						String[] temp = data.split("_");
						if(temp[1].equals(name)) {
							ID = Integer.parseInt(temp[0]);
							taskname = temp[1];
							startDate = LocalDate.parse(temp[2]);
							endDate = LocalDate.parse(temp[3]);
							address = temp[4];
							status = Boolean.parseBoolean(temp[5]);
							task = new Task(ID,taskname,startDate,endDate,address,status);
							return task;
						}
						data = "";
					}else {
						data = data + (char)c;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					reader.close();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public Task findTask(int id, File dataFile)  {
		String data = "";
		Task task = null;
		int c;
		
		try {
			FileInputStream file = new FileInputStream(dataFile);
			reader = new InputStreamReader(file, StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					if(c == (int)'\n') {
						data = data.substring(0, data.length()-1);
						String[] temp = data.split("_");
						if(temp[0].equals(""+id)) {
							ID = Integer.parseInt(temp[0]);
							taskname = temp[1];
							startDate = LocalDate.parse(temp[2]);
							endDate = LocalDate.parse(temp[3]);
							address = temp[4];
							status = Boolean.parseBoolean(temp[5]);
							task = new Task(ID,taskname,startDate,endDate,address,status);
							return task;
						}
						data = "";
					}else {
						data = data + (char)c;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					reader.close();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void editTask(Task task, File dataFile)  {
		String data = "";
		File filetemp = new File("src/data/temp.txt");
		File filetask = dataFile;
		
		int c,i ;
		try {
			reader = new InputStreamReader(new FileInputStream(filetask), StandardCharsets.UTF_8);
			writer = new OutputStreamWriter(new FileOutputStream(filetemp), StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					data = data + (char)c;
					if(c == (int)'\n') {
						i = Integer.parseInt(data.split("_")[0]);
						if(i == task.getId()) {
							writer.write(task.display());
						}else {
							writer.write(data);
						}
						data = "";
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
					reader.close();
					filetask.delete();
					filetemp.renameTo(filetask);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void removeTask(int index, File dataFile) {
		String data = "";
		File filetemp = new File("src/data/temp.txt");
		File filetask = dataFile;
		
		int i = 1, c,j = 0;
		try {
			reader = new InputStreamReader(new FileInputStream(filetask), StandardCharsets.UTF_8);
			writer = new OutputStreamWriter(new FileOutputStream(filetemp), StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					data = data + (char)c;
					if(c == (int)'\n') {
						if(i != index) {
							j++;
							data = data.replaceFirst((data.split("_"))[0], "" + j);
							writer.write(data);
						}
						i++;
						data = "";
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
					reader.close();
					filetask.delete();
					filetemp.renameTo(filetask);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}
	public List<Task> findAll(File dataFile)  {
		String data = "";
		List<Task> task = new ArrayList<>();
		int c;
		
		System.out.println("Path : " + dataFile.getAbsolutePath());
		try {
			FileInputStream file = new FileInputStream(dataFile);
			reader = new InputStreamReader(file, StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					if(c == (int)'\n') {
						String[] temp = data.split("_");
						ID = Integer.parseInt(temp[0]);
						taskname = temp[1];
						startDate = LocalDate.parse(temp[2]);
						endDate = LocalDate.parse(temp[3]);
						address = temp[4];
						status = Boolean.parseBoolean(temp[5]);
						task.add(0,new Task(ID,taskname,startDate,endDate,address,status));
						data = "";
					}else {
						data = data + (char)c;
					}
				}
				return task;
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					reader.close();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
