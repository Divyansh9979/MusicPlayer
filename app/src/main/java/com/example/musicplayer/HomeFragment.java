package com.example.musicplayer;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    Button previous,play,next,note,pause;
    static MediaPlayer mediaPlayer;
    int currentPosition = 0;
    ArrayList<File> mySongs;
    Button notification;
    SeekBar sb;
    Thread updateSeekBar;
    TextView songNameText;
    String sname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        previous = view.findViewById(R.id.btn_previous);
        play = view.findViewById(R.id.btn_play);
        next = view.findViewById(R.id.btn_next);
        note = view.findViewById(R.id.btn_playlist);
        pause=view.findViewById(R.id.btn_pause);
        notification = view.findViewById(R.id.btn_notification);
        mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.music);

        updateSeekBar=new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                //int currentPosition = 0;
                while(currentPosition < totalDuration){
                    try{
                        sleep(500);
                        currentPosition=mediaPlayer.getCurrentPosition();
                        sb.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){

                    }
                }
            }
        };


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "This is an Example of Notification.";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        getContext()
                )
                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                        .setContentTitle("New Notification")
                        .setContentText(message)
                        .setAutoCancel(true);
                NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(0,builder.build());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* mediaPlayer.stop();
               // mediaPlayer.release();

                currentPosition=((currentPosition-1)<0)?(mySongs.size()-1):(currentPosition-1);
                Uri u = Uri.parse(mySongs.get(currentPosition).toString());
                mediaPlayer = MediaPlayer.create(getContext(),u);
                //sname = mySongs.get(position).getName().toString();
                //songNameText.setText(sname);
                mediaPlayer.start();*/
                Toast.makeText(getActivity().getApplicationContext(), "End of Queue", Toast.LENGTH_SHORT).show();

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                mediaPlayer.stop();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mediaPlayer.isPlaying()){
                    mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.music);
                    mediaPlayer.start();
                    pause.setVisibility(View.VISIBLE);
                    play.setVisibility(View.GONE);
                    }
                else
                    mediaPlayer.stop();
                //
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mediaPlayer.stop();
               // mediaPlayer.release();
                currentPosition=((currentPosition+1)%mySongs.size());
                Uri uri = Uri.parse(mySongs.get(currentPosition).toString());
                // songNameText.setText(getSongName);
                mediaPlayer = MediaPlayer.create(getContext(),uri);

                //sname = mySongs.get(position).getName().toString();
                //songNameText.setText(sname);

                try{
                    mediaPlayer.start();
                }catch(Exception e){}*/
                Toast.makeText(getActivity().getApplicationContext(), "End of Queue", Toast.LENGTH_SHORT).show();

            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Tunes.class);
                startActivity(intent);
            }
        });
        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        list.setAdapter(new HorizontalAdapter(new String[]{"Android","Java","C++","C#","PHP"}));


        return view;
    }

}
