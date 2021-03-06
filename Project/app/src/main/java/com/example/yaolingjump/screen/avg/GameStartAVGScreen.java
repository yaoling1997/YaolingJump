package com.example.yaolingjump.screen.avg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yaolingjump.Macro.AVG;
import com.example.yaolingjump.Macro.Macro;
import com.example.yaolingjump.Macro.MyAssets;
import com.example.yaolingjump.MainActivity;
import com.example.yaolingjump.MusicService;
import com.example.yaolingjump.screen.GameScreen;

import loon.action.avg.AVGCG;
import loon.action.avg.AVGDialog;
import loon.action.avg.AVGScreen;
import loon.action.avg.drama.Command;
import loon.component.LMessage;
import loon.component.LPaper;
import loon.component.LSelect;
import loon.event.ActionKey;
import loon.opengl.GLEx;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 2018/2/25.
 */

public class GameStartAVGScreen extends AVGScreen {
    private LPaper btnJump;
    private LPaper yaolingName;
    private LPaper jiangxianName;
    private int marginH=5;
    private int marginV=5;
    public GameStartAVGScreen(){
        super(MyAssets.SCRIPT_GAME_START, AVGDialog.getRMXPDialog(MyAssets.AVG_MESSAGE,
                460, 150));
        setScrCG(new AVGCG(this));//不知道这是干啥的，但是AVGScreen鲁棒性不强，得自己new一个
        yaolingName = new LPaper(MyAssets.NAME_YAOLING);
        yaolingName.setLocation(getWidth()- yaolingName.getWidth()+40,marginV);
        add(yaolingName);
        yaolingName.setVisible(false);

        jiangxianName = new LPaper(MyAssets.NAME_JIANGXIAN);
        jiangxianName.setLocation(marginH,marginV);
        add(jiangxianName);
        jiangxianName.setVisible(false);
    }
    @Override
    public boolean nextScript(String mes) {
        Log.i("yaoling1997","AVG mes:"+mes);
        if (AVG.YAOLING_NAME.equalsIgnoreCase(mes)){//右上角
            yaolingName.setVisible(true);
            jiangxianName.setVisible(false);
        }else if (AVG.JIANGXIAN_NAME.equalsIgnoreCase(mes)){//左上角
            jiangxianName.setVisible(true);
            yaolingName.setVisible(false);
        }else if (AVG.NO_NAME.equalsIgnoreCase(mes)){//不显示名字
            yaolingName.setVisible(false);
            jiangxianName.setVisible(false);
        }
        return true;
    }

    @Override
    public void onSelect(String s, int i) {

    }

    @Override
    public void initMessageConfig(LMessage lMessage) {

    }

    @Override
    public void initSelectConfig(LSelect lSelect) {

    }

    @Override
    public void initCommandConfig(Command command) {

    }

    @Override
    public void drawScreen(GLEx glEx) {

    }

    @Override
    public void onExit() {
        Log.i("yaoling1997","behind setScreen");
        close();
        AVGDialog.clear();
        setScreen(new World1BeforeAVGScreen());
    }

    @Override
    public void onLoading() {
        btnJump = new LPaper(MyAssets.BTN_JUMP){
            ActionKey action= new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);
            @Override
            public void doClick() {
                if (!action.isPressed()){
                    action.press();
                    onExit();
                }
            }
        };
        btnJump.setLocation(getWidth()-btnJump.getWidth()-marginH,getHeight()-btnJump.getHeight()-marginV);
        add(btnJump);

        applyPrefs();
    }
    private void applyPrefs(){
        SharedPreferences prefs= MainActivity.mainActivity.getSharedPreferences(Macro.PREFS_FILE,MODE_PRIVATE);
        Intent intent= new Intent(MainActivity.mainActivity,MusicService.class);
        if (prefs.getString(Macro.BG_MUSIC,Macro.CLOSE).equals(Macro.OPEN)) {
            intent.putExtra(Macro.BG_MUSIC,prefs.getString(Macro.BG_MUSIC,Macro.OPEN));//open or close
        }else {
            intent.putExtra(Macro.BG_MUSIC,prefs.getString(Macro.BG_MUSIC,Macro.CLOSE));//open or close
        }
        intent.putExtra(Macro.BG_MUSIC_SOURCE,MyAssets.WELCOME_BG_MUSIC);
        MainActivity.mainActivity.startService(intent);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
