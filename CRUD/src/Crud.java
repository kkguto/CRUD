import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

class Crud {
    Random ran = new Random();

    public String descricao;
    public String status;
    public int prioridade;
    public int id;

    //Construtor
    public Crud(String d, int s, int p){
        this.descricao = d;

        switch (s) {
            case 1:
                this.status = "Done";
                break;
            case 2:
                this.status = "Doing";
                break;
            case 3:
                this.status = "To do";
                break;
            default:
                System.out.println("Invalid Status");
                throw new AssertionError();
        }

        this.prioridade = p;
        this.id = ran.nextInt(1, 1000); // 1 a 999
    }

    //Metodo para criar as task
    public void AddTask(){
        try {
            FileWriter wr = new FileWriter("TaskManeger.txt", true);
            wr.write(this.id + ";" + this.descricao + ";" + this.prioridade + ";" + this.status + "\n");
            wr.close();
        } catch (Exception e) {
            System.out.println("[ERRO] Fail to write to File");
            e.printStackTrace();
        }
    }

    //Metodo para Listas se a task que existem
    public void ListTask(){
        try {
            FileReader rd = new FileReader("TaskManeger.txt");
            BufferedReader br = new BufferedReader(rd);
            String linha = br.readLine(); // le a primeira linha

            while (linha != null) { 
                System.out.println(linha);
                linha = br.readLine(); // le a proxima linha 
            }
            rd.close();    
            br.close();

        } catch (Exception e) {
            System.out.println("[ERRO] Fail to read the File");
            e.printStackTrace();
        }
    }

    //Metodo para verificar se a task existe
    static boolean SearchTaskId(int n){
        try {
            BufferedReader br = new BufferedReader(new FileReader("TaskManeger.txt"));
            String linha = br.readLine();
            String []arrLinha = new String[4]; //Guardara os valores da linha
            
            while(linha != null){ 
                arrLinha = linha.split(";"); // tranforma a string em um array dividindo em ;
                if(Integer.parseInt(arrLinha[0]) == n){ //Integer.parseInt(arrLinha[0]) transforma o primeiro index em um valor inteiro
                    br.close();
                    return true;
                }
                linha = br.readLine(); // le a proxima linha 
            }
            br.close();

        } catch (Exception e) {
            System.out.println("[ERRO] Fail to read the File");
            e.printStackTrace();
        }

        return false;
    }

    //Metodo para Atualizar a task
    public boolean UpdateTask(int id, Scanner sc){
        boolean exist_id = SearchTaskId(id);

        if(exist_id == false){
            System.out.println("The Task_ID " + id + " does not exists in the file\n");
            return false; // false caso não exista esse id da tarefa
        }

        try {
            File temp = new File("tempFile.txt"); 

            FileWriter temp_wr = new FileWriter(temp);
            BufferedReader br = new BufferedReader(new FileReader("TaskManeger.txt"));

            String linha = br.readLine();
            String []arrLinha = new String[4];

            while(linha != null){
                arrLinha = linha.split(";");
                int id_atual = Integer.parseInt(arrLinha[0]);

                if(id == id_atual){
                    sc.nextLine();
                    System.out.printf("Description: ");
                    String nova_descr = sc.nextLine();
                    System.out.printf("Priority (1 to 10): ");
                    int nova_prio = sc.nextInt();
                    System.out.printf("Status (1 - Done, 2 - Doing, 3 - To Do): ");
                    int novo_status = sc.nextInt();

                    if((nova_prio < 1 && nova_prio > 10) || (novo_status < 1 && novo_status > 3)){
                        System.out.println("[ERRO] Invalid content");
                        return false;
                    }else{
                        String bo_status;
                        switch (novo_status) {
                            case 1:
                                bo_status = "Done";
                                break;
                            case 2:
                                bo_status = "Doing";
                                break;
                            case 3:
                                bo_status = "To do";
                                break;
                            default:
                                System.out.println("Invalid Status");
                                throw new AssertionError();
                        }

                        String updateLine = id + ";" + nova_descr + ";" + nova_prio + ";" + bo_status + "\n";
                        temp_wr.write(updateLine);
                    }
                    linha = br.readLine(); // le a proxima linha 

                }else{
                    temp_wr.write(linha);
                }
            }
            br.close();
            temp_wr.close();

            //Renomeia o novo arquivo, apagando o original
            File mainfile = new File("TaskManeger.txt");
            mainfile.delete();
            temp.renameTo(mainfile);
    
        } catch (Exception e) {
            System.out.println("[ERRO] Fail to write to File");
            return false;
        }

        return true;
    }


    //Metodo para deletar a task
    public boolean DeleteTask(int id, Scanner sc){
        boolean exist_id = SearchTaskId(id);

        if(exist_id == false){
            System.out.println("The Task_ID " + id + " does not exists in the file\n");
            return false; // false caso não exista esse id da tarefa
        }

        try {
            File temp = new File("tempFile.txt"); 

            FileWriter temp_wr = new FileWriter(temp);
            BufferedReader br = new BufferedReader(new FileReader("TaskManeger.txt"));

            String linha = br.readLine();
            String []arrLinha = new String[4];

            while(linha != null){
                arrLinha = linha.split(";");
                int id_atual = Integer.parseInt(arrLinha[0]);

                if(id_atual != id){
                    temp_wr.write(linha + "\n"); // nao faz nada se for a linha do Id
                }

                linha = br.readLine();
            }

            br.close();
            temp_wr.close();

            //Renomeia o novo arquivo, apagando o original
            File mainfile = new File("TaskManeger.txt");
            mainfile.delete();
            temp.renameTo(mainfile);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void ShowTask(int id){
        boolean exist_id = SearchTaskId(id);

        if(!exist_id){
            System.out.println("Task with ID " + id + " not found.");
            return;
        }else{
            try {
                BufferedReader br = new BufferedReader(new FileReader("TaskManeger.txt"));
    
                String linha = br.readLine();
    
                String []arrLinha = new String[4];


                while(linha != null){

                    arrLinha = linha.split(";");
                    int id_atual = Integer.parseInt(arrLinha[0]);

                    if(id_atual == id){
                        System.out.println("\nTask Found:");
                        System.out.println("ID: " + arrLinha[0]);
                        System.out.println("Description: " + arrLinha[1]);
                        System.out.println("Priority: " + arrLinha[2]);
                        System.out.println("Status: " + arrLinha[3]);
                        break;
                    }

                    linha = br.readLine();
                }
    
                br.close();
    
            } catch (Exception e) {
                System.out.println("[ERRO] Fail to read the File");
            }
    
    
        }
    }
}
