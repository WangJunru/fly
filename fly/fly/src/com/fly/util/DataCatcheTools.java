package com.fly.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import com.fly.sdk.Product;
import com.fly.sdk.School;

import android.content.Context;

public class DataCatcheTools {  
	
	  public static void  catcheSchoolData(Context context,ArrayList<School> schools)
	  {
		  if(context == null || schools == null || schools.isEmpty())
		  {
			  return ;
		  }
		  try {
			ObjectOutputStream objOut = new ObjectOutputStream(
					context.openFileOutput("school.bin", Context.MODE_PRIVATE));	
			objOut.writeObject(schools);
			objOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	  }
	  public static ArrayList<School> loadSchoolData(Context context)
	  {
		  if(context == null)
		  {
			  return null;
		  }  
		  try {
			ObjectInputStream  objIn = new ObjectInputStream(context.openFileInput("school.bin"));
			ArrayList<School> ret =  (ArrayList<School>)objIn.readObject();
			objIn.close();
			return ret ;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null ;
	  }
	  
	  public static void  catcheProductData(Context context,ArrayList<Product> products)
	  {
		  if(context == null || products == null || products.isEmpty())
		  {
			  return ;
		  }
		  try {
			ObjectOutputStream objOut = new ObjectOutputStream(
					context.openFileOutput("product.bin", Context.MODE_PRIVATE));	
			objOut.writeObject(products);
			objOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	  }
	  
	  public static  ArrayList<Product> loadProductData(Context context)
	  {
		  if(context == null)
		  {
			  return null;
		  }  
		  try {
			ObjectInputStream  objIn = new ObjectInputStream(context.openFileInput("product.bin"));
			ArrayList<Product> ret =  (ArrayList<Product>)objIn.readObject();
			objIn.close();
			return ret ;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null ;
	  }
}
