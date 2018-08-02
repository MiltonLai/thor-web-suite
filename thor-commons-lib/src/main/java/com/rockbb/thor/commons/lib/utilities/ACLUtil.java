package com.rockbb.thor.commons.lib.utilities;

import java.util.ArrayList;
import java.util.List;

public class ACLUtil {
	public static final int ACL_SIZE = 64;
	public static final int ACL_INT_BASE = '0';

	private ACLUtil() {}

	/**
	 * Get the permission from an ACL string or MASK string by specifying the index
	 * 
	 * @param acl_string
	 * @param index
	 * @return true or false
	 */
	public static boolean IndexOf(String acl_string, int index) {

		// Indicates where the permission unit locates - the index of the char in the ACL string, start from left and zero
		int unit = index / 6;
		// Indicates where the binary value locates in the unit, start from left and zero 
		int step = index %6;

		if (acl_string.length() > unit) {

			// Get the integer value of the char
			int unit_value = acl_string.charAt(unit) - ACL_INT_BASE;
			// Shift move to right to get he binary value
			int step_value = unit_value >> (5 - step) & 1;

			boolean flag = (step_value == 1)? true:false;
			return flag;
		}

		return false;
	}

	/**
	 * Get the calculated permission from a number of ACL strings by specifying the index
	 * 
	 * @param acl_strings
	 * @param index
	 * @return true or false
	 */
	public static boolean IndexOf(String[] acl_strings, int index) {
		for(int i=0; i< acl_strings.length; i++) {

			// The calculation logic is OR - returns true as long as one true
			if (IndexOf(acl_strings[i], index)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the final permission with masks by specifying the index
	 * 
	 * @param acl_strings
	 * @param mask_string
	 * @param index
	 * @return true or false
	 */
	public static boolean IndexOf(String[] acl_strings, String mask_string, int index) {

		if (IndexOf(mask_string, index)) {
			if (IndexOf(acl_strings, index)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the final permission with masks by specifying the index
	 * 
	 * @param acl_strings
	 * @param mask_strings
	 * @param index
	 * @return true or false
	 */
	public static boolean IndexOf(String[] acl_strings, String[] mask_strings, int index) {

		if (IndexOfMask(mask_strings, index)) {
			if (IndexOf(acl_strings, index)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculate the final MASK value, if there are several MASK strings
	 * 
	 * @param mask_strings
	 * @param index
	 * @return
	 */
	public static boolean IndexOfMask(String[] mask_strings, int index) {
		for(int i=0; i< mask_strings.length; i++) {

			// The calculation logic is AND - returns false as long as one false exists
			if (!IndexOf(mask_strings[i], index)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Convert permission string array to compressed string
	 * Each permission string stands for an index of the ALLOWED permission
	 */
	public static String permissionsToString(String[] permissions) {
		int[] acl_data = new int[ACL_SIZE * 6];
		for (int i=0; i<acl_data.length; i++) {acl_data[i]=0;}
		if (permissions != null) {
			for (int i=0; i<permissions.length; i++) {
				try {
					int index = Integer.parseInt(permissions[i]);
					if (index < acl_data.length && index >= 0) acl_data[index] = 1;
				}catch(Exception e) {}
			}
		}
		return intsToString(acl_data);
	}

	/*
	 * Convert permission string array to compressed string
	 * Each permission integer stands for an index of the ALLOWED permission
	 */
	public static String permissionsToString(int[] permissions) {
		int[] acl_data = new int[ACL_SIZE * 6];
		for (int i=0; i<acl_data.length; i++) {acl_data[i]=0;}
		if (permissions != null) {
			for (int i=0; i<permissions.length; i++) {
				int index = permissions[i];
				if (index < acl_data.length && index >= 0) acl_data[index] = 1;
			}
		}
		return intsToString(acl_data);
	}

	/**
	 * Convert boolean array to compressed string
	 * FALSE will be filled at right if the array is shorten than ACL_SIZE*6
	 * The exceed part will be omitted if the array is larger than ACL_SIZE*6
	 *
	 * @param flags
	 * @return
	 */
	public static String flagsToString(boolean[] flags) {
		int[] ints = new int[ACL_SIZE * 6];
		for (int i=0; i<flags.length && i< ACL_SIZE * 6; i++) {
			ints[i] = (flags[i]) ? 1:0;
		}
		return buildString(ints);
	}

	/**
	 * Convert integer array to compressed string
	 * 0 will be filled at right of the array if the array is shorten than ACL_SIZE*6
	 * 
	 * @param intPermissions
	 * @return
	 */
	public static String intsToString(int[] intPermissions) {
		int[] ints = new int[ACL_SIZE * 6];
		for (int i=0; i<intPermissions.length && i< ACL_SIZE * 6; i++) {
			ints[i] = (intPermissions[i] == 0) ? 0:1;
		}
		return buildString(ints);
	}

	/**
	 * Convert a string to a boolean array, 
	 * FALSE will be filled at right if the string is shorten than ACL_SIZE
	 * The right characters will be omitted if the string is longer than ACL_SIZE
	 * 
	 * @param acl_string
	 * @return
	 */
	public static boolean[] stringToFlags(String acl_string) {
		boolean[] flags = new boolean[ACL_SIZE * 6];
		for (int i=0; i<acl_string.length() && i<ACL_SIZE; i++) {
			int value = acl_string.charAt(i) - ACL_INT_BASE;
			for (int j=0; j<6; j++) {
				flags[i*6 + j] = ((value >> (5 - j) & 1) == 0)? false:true;
			}
		}
		return flags;
	}

	/**
	 * Convert a string to an integer array, 
	 * 0 will be filled at right if the string is shorten than ACL_SIZE
	 * The right characters will be omitted if the string is longer than ACL_SIZE
	 *  
	 * @param acl_string
	 * @return
	 */
	public static int[] stringToInts(String acl_string) {
		int[] ints = new int[ACL_SIZE * 6];
		for (int i=0; i<acl_string.length() && i<ACL_SIZE; i++) {
			int value = acl_string.charAt(i) - ACL_INT_BASE;
			for (int j=0; j<6; j++) {
				ints[i*6 + j] = value >> (5 - j) & 1;
			}
		}
		return ints;
	}

	/**
	 * Extract a binary value list from the compressed string 
	 * 
	 * @param acl_string
	 * @return
	 */
	public static String extract(String acl_string) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<acl_string.length(); i++) {
			int value = acl_string.charAt(i) - ACL_INT_BASE;
			for (int j=0; j<6; j++) {
				char c = ((value >> (5 - j) & 1) == 1) ? '1':'0';
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Compress the binary value list to a fixed width string
	 * 
	 * @param acl_bin_string
	 * @return
	 */
	public static String compress(String acl_bin_string) {
		acl_bin_string = (acl_bin_string.length() >= ACL_SIZE * 6)?
				acl_bin_string.substring(0, ACL_SIZE * 6) : String.format("%-" + ACL_SIZE * 6 + "s", acl_bin_string);
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<ACL_SIZE; i++) {
			char c = '0';
			for(int j=0; j<6; j++) {
				c += (acl_bin_string.charAt(i*6 + j) == '1')? 1 << (5 - j) : 0;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	private static String buildString(int[] ints) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i< ACL_SIZE; i++) {
			int value = 0;
			for(int j=0; j<6; j++) {
				value += ints[i *6 + j] << (5 - j);
			}
			char c = (char)(value+ACL_INT_BASE);
			sb.append(c);
		}
		return sb.toString();
	}

	public static List<Integer> stringToPermissions(String acl_string) {
		int[] ints = stringToInts(acl_string);
		List<Integer> output = new ArrayList<>();
		for (int i = 0; i < ints.length; i++)
		{
			if (ints[i] == 1)
			{
				output.add(i);
			}
		}
		return output;
	}

	/*
	 * Convert permission string list to compressed string
	 * Each permission integer stands for an index of the ALLOWED permission
	 */
	public static String permissionsToString(List<Integer> permissions) {
		int[] acl_data = new int[ACL_SIZE * 6];
		for (int i=0; i<acl_data.length; i++) {acl_data[i]=0;}
		if (permissions != null) {
			for (int i=0; i<permissions.size(); i++) {
				int index = permissions.get(i);
				if (index < acl_data.length && index >= 0) acl_data[index] = 1;
			}
		}
		return intsToString(acl_data);
	}

	/*
	 * Test cases
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();

		// How to build an ACL string
		int[] acl_data = new int[ACL_SIZE * 6];
		for (int i=0; i<acl_data.length; i++) {acl_data[i]=0;}
		for (int i=0; i<64; i++) {acl_data[i]=1;}
		String acl_string = intsToString(acl_data);
		System.out.println(acl_string);
		// Build end
	}
}
