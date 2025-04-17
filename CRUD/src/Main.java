import  java.io.File;
import java.util.Scanner;

public class Main {

    static void Menu(){
        System.out.println("===========MENU===========\n==========================");
        System.out.println("[1] Add Task");
        System.out.println("[2] List Tasks");
        System.out.println("[3] Update Task");
        System.out.println("[4] Delete Task");
        System.out.println("[5] Search Task");
        System.out.println("[6] Exit");
        System.out.println("=========================");
        System.out.printf("Type your choice: ");
    }

    static Crud CreateObject(Scanner sc){
        sc.nextLine(); // Consome quebra de linha
        System.out.printf("Description: ");
        String desc = sc.nextLine();
        System.out.printf("Priority (1 to 10): ");
        int prio = sc.nextInt();
        System.out.printf("Status (1 - Done, 2 - Doing, 3 - To Do): ");
        int status = sc.nextInt();

        if(prio < 1 || prio > 10 || status < 1 || status > 3){
            System.out.println("[ERRO] Invalid content");
            return null;
        }

        Crud task = new Crud(desc, status, prio);
        return task;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Crud task = null;

        //Criação do Arquivo
        try {
            File fi = new File("TaskManeger.txt");

            if(fi.createNewFile()){
                System.out.println("File create : " + fi.getName());
            }else{
                System.out.println("File already exists");
            }

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int escolha;
        

        do{
            Menu();
            escolha = sc.nextInt();
            int id;
            
            switch(escolha){
                case 1 :
                    task = CreateObject(sc);
                    if(task != null) task.AddTask();
                    break;

                case 2 :
                    if (task != null) {
                        task.ListTask();
                    } else {
                        System.out.println("There is no task create");
                    }
                    break;

                case 3 :
                    System.out.print("Type the ID that you want to update: ");
                    id = sc.nextInt();
                    
                    if (task != null) {
                        task.UpdateTask(id, sc);
                    } else {
                        System.out.println("There is no task create");
                    }
                    break;

                case 4 :
                    System.out.print("Type the ID that you want to delete: ");
                    id = sc.nextInt();
                    
                    if (task != null) {
                        task.DeleteTask(id, sc);
                    } else {
                        System.out.println("There is no task create");
                    }
                    break;

                case 5 :
                    System.out.print("Type the ID that you want to delete: ");
                    id = sc.nextInt();
            
                    task.ShowTask(id);
                    break;

                case 6:
                    System.out.println("App Close");
                    break;
                
                default :
                    System.out.println("[ERRO] Invalid Option");
                    break;
            }

        }while(escolha != 5);

        sc.close();
    }
}
