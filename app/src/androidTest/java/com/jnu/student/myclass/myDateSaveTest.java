package com.jnu.student.myclass;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.jnu.student.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class myDateSaveTest {
    myDateSave dataSaverBackup;
    ArrayList<ShopItem> shopItemsBackup;

    @Before
    public void setUp() throws Exception {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup = new myDateSave(targetContext);
        shopItemsBackup = dataSaverBackup.load();
    }

    @After
    public void tearDown() throws Exception {
        dataSaverBackup.save(shopItemsBackup);
    }

    @Test
    public void save() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        myDateSave dataSaver = new myDateSave(targetContext);
        ArrayList<ShopItem> shopItems=new ArrayList<>();
        ShopItem shopItem=new ShopItem(R.drawable.bai_cai, "测试",56.7);
        shopItems.add(shopItem);
        shopItem=new ShopItem(R.drawable.luo_bo, "正常",12.3);
        shopItems.add(shopItem);
        dataSaver.save(shopItems);

        myDateSave dataLoader = new myDateSave(targetContext);
        ArrayList<ShopItem> shopItemsRead=dataLoader.load();
        assertNotSame(shopItems,shopItemsRead);
        assertEquals(shopItems.size(),shopItemsRead.size());
        for(int index=0;index<shopItems.size();++index)
        {
            assertNotSame(shopItems.get(index),shopItemsRead.get(index));
            assertEquals(shopItems.get(index).getName(),shopItemsRead.get(index).getName());
            assertEquals(shopItems.get(index).getPrice(),shopItemsRead.get(index).getPrice(),1e-2);
            assertEquals(shopItems.get(index).getImageResource(),shopItemsRead.get(index).getImageResource());
        }

    }

    @Test
    public void load() {
    }
}