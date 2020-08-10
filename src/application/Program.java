package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Products;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Products> list = new ArrayList<>();

		System.out.print("Enter the file path: ");
		String strpatch = sc.nextLine();
		File file = new File(strpatch);
		
		String folder = file.getParent();
		
		
		boolean sub = new File(strpatch +"\\out").mkdir();
		System.out.println("directory created successfully");
		
		String targetFileStr = folder + "\\out\\summary.csv";
		
		//Lê o arquivo dos produtos
		try (BufferedReader br = new BufferedReader(new FileReader(strpatch))){
			
			String line = br.readLine();
			
			while (line != null) {
				String [] data = line.split(",");
				String name = data[0];
				double price = Double.parseDouble(data[1]);
				int quantity = Integer.parseInt(data[2]);
				
				list.add(new Products(name, price, quantity));
				line = br.readLine();
				
			}
			
			//Cria o novo arquivo baseado no arquivo de produtos
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
				File[] folders = file.listFiles(File::isDirectory);
				
				for(Products p: list) {
					bw.write(p.getName()+ ","+ String.format("%.2f", p.totalValue()));
					bw.newLine();
				}

				System.out.println(targetFileStr + " CREATED!");
				
				
				
				
			} catch (IOException e) {
				System.out.println("Error writing the file: "+e.getMessage());
			}
		} catch (IOException e) {
			System.out.println("Error reading the file: "+e.getMessage());
		
		}
		sc.close();
	}

}
