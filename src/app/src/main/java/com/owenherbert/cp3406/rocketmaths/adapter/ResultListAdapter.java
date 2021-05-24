package com.owenherbert.cp3406.rocketmaths.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.owenherbert.cp3406.rocketmaths.R;
import com.owenherbert.cp3406.rocketmaths.utility.TwitterManager;
import com.owenherbert.cp3406.rocketmaths.database.entity.result.Result;
import com.owenherbert.cp3406.rocketmaths.game.GameDifficulty;

import java.util.List;

/**
 * The ResultListAdapter class extends ArrayAdapter<Result> and is used to display the highscores
 * of users in a list view.
 *
 * @author Owen Herbert
 */
public class ResultListAdapter extends ArrayAdapter<Result> {

    private final Context context;
    private final int resource;
    private final GameDifficulty gameDifficulty;

    public ResultListAdapter(@NonNull Context context, int resource, @NonNull List<Result> objects,
                             GameDifficulty gameDifficulty) {

        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.gameDifficulty = gameDifficulty;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String strNumber = String.valueOf(position + 1);
        String strNickname = getItem(position).getUserNickname();
        String strPoints = String.valueOf(getItem(position).getPoints());
        String strCorrect = String.valueOf(getItem(position).getTallyCorrect());
        String strIncorrect = String.valueOf(getItem(position).getTallyIncorrect());

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView highscoreNumberTextView = convertView.findViewById(R.id.highscoreNumberTextView);
        TextView highscoreNicknameTextView = convertView.findViewById(R.id.usernameTextView);
        TextView highscorePointsTextView = convertView.findViewById(R.id.pointsTextView);
        TextView highscoreTallyCorrectTextView = convertView.findViewById(R.id.tallyCorrectTextView);
        TextView highscoreTallyIncorrectTextView = convertView.findViewById(R.id.tallyIncorrectTextView);

        highscoreNumberTextView.setText(strNumber);
        highscoreNicknameTextView.setText(strNickname);
        highscorePointsTextView.setText
                (String.format(context.getString(R.string.highscore_total_points), strPoints));

        highscoreTallyCorrectTextView.setText
                (String.format(context.getString(R.string.highscore_amnt_correct), strCorrect));

        highscoreTallyIncorrectTextView.setText
                (String.format(context.getString(R.string.highscore_amnt_incorrect), strIncorrect));

        // how many answers did the user submit
        int answerCount = Integer.parseInt(strCorrect) + Integer.parseInt(strIncorrect);

        // format message to send to twitter
        String msg = String.format(context.getString(R.string.twitter_share_format), strNickname,
                strPoints, gameDifficulty.toString(), answerCount, strCorrect, strIncorrect);

        // open dialog when user clicks score list item
        convertView.setOnClickListener(view -> new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.dialog_share_description))
                .setTitle(context.getString(R.string.dialog_share_title))
                .setPositiveButton(context.getString(R.string.dialog_positive),
                        (dialogInterface, i) -> TwitterManager.tweet(msg))
                .setNegativeButton(context.getString(R.string.dialog_negative),
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show());

        return convertView;
    }
}
