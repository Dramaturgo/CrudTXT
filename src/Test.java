import com.jakewharton.fliptables.FlipTable;
import com.jakewharton.fliptables.FlipTableConverters;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

    public static void guardarListaDePersonas(File file, List<Persona> lista){

      try{
          FileOutputStream ficheroSalida= new FileOutputStream (file);
          ObjectOutputStream objetoSalida=new ObjectOutputStream (ficheroSalida);
          objetoSalida.writeObject (lista);
          objetoSalida.close ();
      }catch (FileNotFoundException e){
          System.out.println ("El fichero no exite");
      } catch(Exception e){
          System.out.println (e.getMessage ());
      }

    }

    public static List<Persona>obtenerListaDePersona(File file){

        List<Persona>lista= new ArrayList<> ();
        try{
            FileInputStream fis=new FileInputStream (file);
            ObjectInputStream ois=new ObjectInputStream (fis);
            lista=(List<Persona>)ois.readObject ();
            ois.close ();
        }catch(FileNotFoundException e){
            System.out.println ("Fichero no existe");
        }catch (Exception e){
            System.out.println (e.getMessage ());
        }
        return lista;
    }

    public static void main (String[] args) {

       File file=new File ("C:\\ProyectoTxt\\src\\BasePersona.txt");
       int opcion =0;
       List<Persona>objetivo=obtenerListaDePersona (file);
        Scanner sc=new Scanner (System.in);
        do{
            System.out.println ("Registro de Persona");
            System.out.println ("===================\n");
            System.out.println ("1-Ingresar una nueva Persona");
            System.out.println ("2-Listar registro de Personas");
            System.out.println ("3-Buscar Persona");
            System.out.println ("4-Eliminar Persona");
            System.out.println ("5-Modificar datos de persona");
            System.out.println ("6-Salir");
            System.out.println ("\nIngresar una opcion\n");
            opcion=sc.nextInt ();

            switch (opcion){
                case 1:
                    System.out.println ("Registro de Persona");
                    System.out.println ("===================\n");
                    System.out.println ("Ingrese los siguientes datos:\n");
                    sc.nextLine ();
                    System.out.println ("Nombre:");
                    String nombre=sc.nextLine ();
                    System.out.println ("Dni:");
                    String dni=sc.nextLine ();
                    System.out.println ("Edad:");
                    int edad=sc.nextInt ();
                    sc.nextLine ();
                    System.out.println ("Sexo:");
                    String sexo=sc.nextLine ();
                    objetivo.add (new Persona (nombre,dni,edad,sexo));
                    guardarListaDePersonas (file,objetivo);
                    break;
                case 2:
                    System.out.println ("Listar datos de Persona");
                    System.out.println ("=======================\n");
                    System.out.println (FlipTableConverters.fromIterable (objetivo,Persona.class));
                    break;
                case 3:
                    System.out.println ("Buscar Persona");
                    System.out.println ("===============\n");
                    System.out.println ("Ingresa dni de la persona");
                    String buscado=sc.next ();
                    String mensaje="No se encontro a la persona\n";
                    Persona x=null;
                    for (Persona o:objetivo){
                        if(o.getDni ().equals (buscado)){
                            mensaje="Persona encontrada\n";
                            x=o;
                        }
                    }
                    System.out.println ("\n"+mensaje);
                    String []headers={"Nombre","Dni","Edad","Sexo"};
                    if(x!=null){
                        String [][] data={{x.getNombre (),x.getDni (),String.valueOf (x.getEdad ()),x.getSexo ()}};
                        System.out.println (FlipTable.of(headers,data));
                    }
                    break;
                case 4:
                    System.out.println ("Eliminar Persona");
                    System.out.println ("================\n");
                    System.out.println ("Ingrese el dni de la persona");
                    String eliminar=sc.next ();
                    String mensaje2="No se encontro a la persona\n";
                    for (int i=0;i< objetivo.size ();i++){

                        if(objetivo.get (i).getDni ().equals (eliminar)){
                            objetivo.remove (i);
                            mensaje2="Persona eliminada\n";
                        }
                    }
                    guardarListaDePersonas (file,objetivo);
                    System.out.println (mensaje2);
                    break;
                case 5:
                    System.out.println ("Modificar Persona");
                    System.out.println ("=================\n");
                    System.out.println ("Ingrese el dni de la persona");
                    String modificar=sc.next ();
                    String mensaje3="No se encontro a la persona\n";
                    Persona persona=null;
                    for(Persona o:objetivo){
                        if(o.getDni ().equals (modificar)){
                            persona=o;
                            mensaje3="Persona encontrada";
                        }
                    }
                    System.out.println (mensaje3);
                    int opcion2=0;
                    if(persona!=null){
                        do{
                            System.out.println ("1-Modificar nombre");
                            System.out.println ("2-Modificar dni");
                            System.out.println ("3-Modificar edad");
                            System.out.println ("4-Modificar sexo");
                            System.out.println ("5-Cancelar");
                            opcion2=sc.nextInt ();
                            switch (opcion2){
                                case 1:
                                    sc.nextLine ();
                                    System.out.println ("Nombre actual: "+persona.getNombre ());
                                    persona.setNombre (sc.nextLine ());
                                    break;
                                case 2:
                                    System.out.println ("Dni actual: "+persona.getDni ());
                                    persona.setDni (sc.nextLine ());
                                    break;
                                case 3:
                                    System.out.println ("Edad actual "+persona.getEdad ());
                                    persona.setEdad (sc.nextInt ());
                                    sc.nextLine ();
                                    break;
                                case 4:
                                    System.out.println ("Sexo actual "+persona.getSexo ());
                                    persona.setSexo (sc.nextLine ());
                                    break;
                                case 5:
                                    System.out.println ("\nOpcion cancelada\n");
                                    guardarListaDePersonas (file,objetivo);
                                    break;
                                default:
                                    System.out.println ("\nOpcion invalida\n");
                            }
                        }while (opcion2!=5);
                    }
                    break;
                case 6:
                    System.out.println ("Saliendo del programa");
                    break;
                default:
                    System.out.println ("Opcion invalida");
            }


        }while (opcion!=6);

    }
}
