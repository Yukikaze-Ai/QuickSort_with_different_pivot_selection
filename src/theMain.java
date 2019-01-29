/*CS3345 PROJECT 5
 * HONGYUN DU
 * HXD171530
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.Scanner;

public class theMain {
	static int CUTOFF = 10; //if the size of a subArray is 10 or less than it, just call insertion sort, which is faster than Quick sort.
	static Random rnd = new Random();
	static String SortedFile="sorted.txt";
	static String UnSortedFile="unsorted.txt";

	public static void main(String[] ags) {
		Long time;
		Long Newtime;
		final int BOUND = 50000;
		Scanner sc = new Scanner(System.in);
		int SIZE;

		int array[] = {};
		do {
			//asking for the array size
			System.out.print("Please enter the Size of the array: " );
			SIZE = sc.nextInt();
			if (SIZE > 0)
				array = new int[SIZE];
			else {
				System.out.println("Invalid Size");
			}
		} while (SIZE <= 0);
		sc.close();
		System.out.println("The Array size is:  " + SIZE);
		
		
		for (int i = 0; i < array.length; i++) {
			array[i] = rnd.nextInt(BOUND);//generate the array
		}
		File USFile = new File(UnSortedFile);
		try {
			USFile.createNewFile();
		BufferedWriter out1 = new BufferedWriter(new FileWriter(USFile));
		for(int each:array)
			out1.write(each+"\n");
		out1.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
			
		//copy the array
		int array2[] = Arrays.copyOf(array, array.length);
		int array3[] = Arrays.copyOf(array, array.length);
		int array4[] = Arrays.copyOf(array, array.length);
		
		File outputFile = new File(SortedFile);
		try {
		outputFile.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
		
		
		time = System.nanoTime();
		FirstQSort(array, 0, array.length - 1);//using the First pivot to sort the array
		Newtime = System.nanoTime() - time;
		System.out.println("In-Place quick sort with the first element as pivot: \t" + Newtime + " ns.");
		System.out.println("Does this array have proper order? " + arrayCheck(array));
		out.write("Fist:\t");
		for (int each:array)
			out.write(String.valueOf(each)+" ");

		time = System.nanoTime();
		RandomQSort(array3, 0, array3.length - 1);//using the Random quick sort to sort the array
		Newtime = System.nanoTime() - time;
		System.out.println("In-Place quick sort with ramdom pivot: \t\t\t" + Newtime + " ns.");
		System.out.println("Does this array have proper order? " + arrayCheck(array3));
		out.write("\nSecond:\t");
		for (int each:array3)
			out.write(String.valueOf(each)+" ");
		
		time = System.nanoTime();
		Random3QSort(array4, 0, array4.length - 1);
		Newtime = System.nanoTime() - time;
		System.out.println("In-Place quick sort with with Median of 3 ramdom pivot: " + Newtime + " ns.");
		System.out.println("Does this array have proper order? " + arrayCheck(array4));
		out.write("\nThird:\t");
		for (int each:array4)
			out.write(String.valueOf(each)+" ");

		time = System.nanoTime();
		BookQSort(array2, 0, array2.length - 1);
		Newtime = System.nanoTime() - time;
		System.out.println("In-Place quick sort with Median-3 pivot: \t\t" + Newtime + " ns.");
		System.out.println("Does this array have proper order? " + arrayCheck(array2));
		out.write("\nFourth:\t");
		for (int each:array2)
			out.write(String.valueOf(each)+" ");
		out.close();
		}catch (Exception e)
		{
			System.out.println(e);
		}

		
	}

	public static void FirstQSort(int ary[], int left, int right) {

		if (left + CUTOFF <= right) {
			int temp = ary[left];
			int l = left, r = right - 1;
			// swap the Pivot
			swap(ary, left, right);
			while (l < r) {
				while (l < right && ary[l] < temp) {
					l++;
				}
				while (r > left && ary[r] >= temp) {
					r--;
				}
				if (l < r)
					swap(ary, l, r);
				else
					break;
			}
			swap(ary, l, right);
			FirstQSort(ary, left, r);
			FirstQSort(ary, l + 1, right);
		} else {
			InsertionSort(ary, left, right);
		}

	}

	public static void RandomQSort(int ary[], int left, int right) {

		if (left + CUTOFF <= right) {
			int PvtInd = rnd.nextInt((right+1) - left) + left;
			int temp = ary[PvtInd];
			int l = left, r = right - 1;
			// swap the Pivot
			swap(ary, PvtInd, right);
			while (l < r) {
				while (l < right && ary[l] < temp) {
					l++;
				}
				while (r > left && ary[r] >= temp) {
					r--;
				}
				if (l < r)
					swap(ary, l, r);
				else
					break;
			}
			swap(ary, l, right);
			RandomQSort(ary, left, r);
			RandomQSort(ary, l + 1, right);
		} else {
			InsertionSort(ary, left, right);
		}

	}

	public static void Random3QSort(int ary[], int left, int right) {

		if (left + CUTOFF <= right) {
			int PvtInd;
			int PvtInd1 = rnd.nextInt((right+1) - left) + left;
			int PvtInd2 = rnd.nextInt((right+1) - left) + left;
			int PvtInd3 = rnd.nextInt((right+1) - left) + left;
			if (ary[PvtInd1] < ary[PvtInd2])
				if (ary[PvtInd3] < ary[PvtInd1]) // P1<P2
					PvtInd = PvtInd1;// P3<P1<P2
				else {//P1<P3, P1<P2
					if (ary[PvtInd3] < ary[PvtInd2])//
						PvtInd = PvtInd3;//P1<P3<P2
					else
						PvtInd = PvtInd2;//P1<P2<P3
				}
			else { // p2<=p1
				if (ary[PvtInd3] < ary[PvtInd2])
					PvtInd = PvtInd2;//P3<P2<P1
				else {//P2<P3,P2<P1
					if (ary[PvtInd3] < ary[PvtInd1])
						PvtInd = PvtInd3;//P2<P3<P1
					else
						PvtInd = PvtInd1;//P2<P1<P3
				}
			}

			int temp = ary[PvtInd];
			int l = left, r = right - 1;
			// swap the Pivot
			swap(ary, PvtInd, right);
			while (l < r) {
				while (l < right && ary[l] < temp) {
					l++;
				}
				while (r > left && ary[r] >= temp) {
					r--;
				}
				if (l < r)
					swap(ary, l, r);
				else
					break;
			}
			swap(ary, l, right);
			RandomQSort(ary, left, r);
			RandomQSort(ary, l + 1, right);
		} else {
			InsertionSort(ary, left, right);
		}

	}

	static void BookQSort(int a[], int left, int right) {
		if (left + CUTOFF <= right) {
			int Pivot = Median(a, left, right);

			int i = left, j = right - 1;
			while (true) {
				while (a[++i] < Pivot) {
				}
				while (a[--j] > Pivot) {
				}
				if (i < j)
					swap(a, i, j);
				else
					break;
			}
			swap(a, i, right - 1);
			BookQSort(a, left, i - 1);
			BookQSort(a, i + 1, right);
		} else {
			InsertionSort(a, left, right);
		}
	}

	static int Median(int ary[], int left, int right) {
		int center = (left + right) / 2;
		if (ary[center] < ary[left])
			swap(ary, left, center);
		if (ary[right] < ary[left])
			swap(ary, left, right);
		if (ary[right] < ary[center])
			swap(ary, center, right);
		// place pivot at position right-1
		swap(ary, center, right - 1);
		return ary[right - 1];
	}

	static void InsertionSort(int a[], int l, int r) {
		int j;
		for (int p = l; p <= r; p++) {
			int temp = a[p];
			for (j = p; j > l && temp < a[j - 1]; j--)
				a[j] = a[j - 1];
			a[j] = temp;
		}
	}

	static void swap(int a[], int l, int r) {
		//swap two values
		int temp = a[l];
		a[l] = a[r];
		a[r] = temp;
	}

	static boolean arrayCheck(int a[]) {
		//check the array weather is sorted or not
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] > a[j])
					return false;
			}
		}
		return true;
	}
}
