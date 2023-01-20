package melo_beat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;

import java.util.concurrent.atomic.AtomicInteger;

import melo_beat.models.SongInfo.Result;
import melo_beat.models.SongInfo.SongInfo;
import melo_beat.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongActivity extends AppCompatActivity {

    Result song;
    ImageView songCover;
    TextView title;
    TextView artists;
    TextView downloads;
    TextView lyric;
    ImageButton playButton;
    ImageButton forwardButton;
    ImageButton backwardButton;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);


        songCover = findViewById(R.id.imageView6);
        title = findViewById(R.id.textView12);
        artists = findViewById(R.id.textView13);
        downloads = findViewById(R.id.textView14);
        playButton = findViewById(R.id.imageButton2);
        forwardButton = findViewById(R.id.imageButton3);
        backwardButton = findViewById(R.id.imageButton);
        lyric = findViewById(R.id.textView15);

        String songID = getIntent().getStringExtra("SongID");

        RetrofitClient.getInstance().getMyApi().getSongInfo(songID).enqueue(new Callback<SongInfo>() {
            @Override
            public void onResponse(Call<SongInfo> call, Response<SongInfo> response) {
                song = response.body().getResult();

                Glide
                        .with(songCover)
                        .load(song.getImage().getCover().getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(songCover);

                title.setText(song.getTitle());
                artists.setText(song.getArtists().get(0).getFullName());
                downloads.setText(song.getDownloadCount());

                AtomicInteger data = new AtomicInteger(12);
                playButton.setOnClickListener(v -> {

                    Handler handler = new Handler();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer == null) {
                                Glide
                                        .with(playButton)
                                        .load(R.drawable.pause_button)
                                        .into(playButton);
                                mediaPlayer = MediaPlayer.create(SongActivity.this, Uri.parse(song.getAudio().getHigh().getUrl()));
                                mediaPlayer.start();

                            } else {
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause();
                                    data.set(mediaPlayer.getCurrentPosition());
                                    Glide
                                            .with(playButton)
                                            .load(R.drawable.play_button)
                                            .into(playButton);
                                } else {
                                    Glide
                                            .with(playButton)
                                            .load(R.drawable.pause_button)
                                            .into(playButton);
                                    mediaPlayer.start();
                                    mediaPlayer.seekTo(data.get());
                                }
                            }
                        }
                    });

                });


                forwardButton.setOnClickListener(it -> {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
                });

                backwardButton.setOnClickListener(it -> {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
                });
                lyric.setText(song.getLyrics());

            }

            @Override
            public void onFailure(Call<SongInfo> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}