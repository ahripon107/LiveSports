package com.androidfragmant.cricket.allabout.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.androidfragmant.cricket.allabout.ParseApplication;
import com.androidfragmant.cricket.allabout.R;
import com.androidfragmant.cricket.allabout.SmackListener;
import com.androidfragmant.cricket.allabout.adapter.BuzzFeedAdapter;
import com.androidfragmant.cricket.allabout.model.BuzzFeed;
import com.androidfragmant.cricket.allabout.util.SharedPrefData;

import org.jivesoftware.smack.packet.Message;

import java.util.Calendar;

/**
 * Created by Ripon on 10/14/16.
 */

public class ChattingFragment extends Fragment {

    private ParseApplication myApplication;

    private ListView listMessage;

    private BuzzFeedAdapter buzzFeedAdapter;

    private EditText messageBody;

    private ImageButton send;

    private boolean smackLoginFlag = false;

    private Context context;

    private static boolean isVisible = false;

    private SmackListener smackListener = new SmackListener() {
        @Override
        public void onLoginSuccess() {
            smackLoginFlag = true;

            Toast.makeText(getContext(), "Joined Succesfully!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLoginFailed() {
            smackLoginFlag = false;

            Toast.makeText(getContext(), "login failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onReceiveMessage(Message message) {
            if (message.getBody() != null) {
                String from = message.getFrom();
                from = from.substring(from.indexOf('/')+1);
                BuzzFeed buzzFeed = new BuzzFeed();
                buzzFeed.setSender(from);
                buzzFeed.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
                buzzFeed.setFeed(message.getBody());
                buzzFeedAdapter.addBuzzFeed(buzzFeed);

                if(!isVisible) {
                    Vibrator vibrator;
                    vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000); // vibrate for 1 second
                }
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        buzzFeedAdapter = new BuzzFeedAdapter(context);
        myApplication = (ParseApplication) context.getApplicationContext();
        myApplication.setSmackListener(smackListener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messenger, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listMessage = (ListView) view.findViewById(R.id.listMessage);
        listMessage.setAdapter(buzzFeedAdapter);

        messageBody = (EditText) view.findViewById(R.id.messageBody);
        send = (ImageButton) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(messageBody.getText().toString())) {
                    Toast.makeText(getContext(), "Please Write Something!", Toast.LENGTH_LONG).show();
                    return;
                }

                Message msg = new Message();
                msg.setTo("livestreamenglish@conference."+ParseApplication.SERVICE);
                msg.setType(Message.Type.groupchat);
                msg.setBody(messageBody.getText().toString());
                messageBody.setText("");
                try {
                    if(myApplication.getmMultiUserChat() != null) myApplication.getmMultiUserChat().sendMessage(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_messenger, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.join:
                Toast.makeText(getContext(),"Please Wait...",Toast.LENGTH_LONG).show();
                myApplication.join();
                return true;
            case R.id.nickname:
                showDialogForNickNameChange();
            default:
                return false;
        }
    }

    @Override
    public void onDetach() {
        myApplication.setSmackListener(null);

        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisible = isVisibleToUser;
    }

    private void showDialogForNickNameChange() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_nickname_change);
        dialog.setTitle("Set Nick Name");

        final EditText inputNickName = (EditText) dialog.findViewById(R.id.inputNickName);
        inputNickName.setText(SharedPrefData.getNickName(getContext()));

        Button ok = (Button) dialog.findViewById(R.id.ok);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ok:
                        String newNickName = inputNickName.getText().toString();
                        if(!TextUtils.isEmpty(newNickName)) {
                            SharedPrefData.setNickName(getContext(), newNickName);
                            myApplication.setNewNickName(newNickName);
                        }

                        dialog.dismiss();
                        break;
                    case R.id.cancel:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        };

        ok.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);

        dialog.show();
    }

}
