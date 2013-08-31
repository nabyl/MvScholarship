package com.yoosufnabeel.mvscholarships;

import java.util.ArrayList;
import java.util.List;

public class Datasource
{

	private static Datasource datasource = null;
	
	private ArrayList<Scholarship> data = null;
	
	private int size = 0;
	
	public static Datasource getInstance( ArrayList<Scholarship> scholarships )
	{
		if (datasource == null)
		{
			datasource = new Datasource(scholarships);
		}
		return datasource;
	}
	
	private Datasource(ArrayList<Scholarship> scholarships)
	{
        size = scholarships.size();
		data = scholarships;

	}

    public void addData(Scholarship scholarship)
    {
        if(data != null)
        {
            data.add(scholarship);
            size = data.size();
        }
    }
	
	public int getSize()
	{
		return size;
	}

	public ArrayList<Scholarship> getData(int offset, int limit)
	{
        ArrayList<Scholarship> newList = new ArrayList<Scholarship>(limit);
		int end = offset + limit;
		if (end > data.size())
		{
			end = data.size();
		}
		newList.addAll(data.subList(offset, end));
		return newList;		
	}

}