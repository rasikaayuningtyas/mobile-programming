package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_name")
public class MainData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "text")
    private String group;
    private int streamingNumber;
    private int currentStream;

    private int votingNumber;
    private int currentVoting;

    private int mentionNumber;
    private int currentMention;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStreamingNumber() {
        return streamingNumber;
    }

    public void setStreamingNumber(int streamingNumber) {
        this.streamingNumber = streamingNumber;
    }

    public int getVotingNumber() {
        return votingNumber;
    }

    public void setVotingNumber(int votingNumber) {
        this.votingNumber = votingNumber;
    }

    public int getMentionNumber() {
        return mentionNumber;
    }

    public void setMentionNumber(int mentionNumber) {
        this.mentionNumber = mentionNumber;
    }

    public int getCurrentStream() {
        return currentStream;
    }

    public void setCurrentStream(int currentStream) {
        this.currentStream = currentStream;
    }

    public int getCurrentVoting() {
        return currentVoting;
    }

    public void setCurrentVoting(int currentVoting) {
        this.currentVoting = currentVoting;
    }

    public int getCurrentMention() {
        return currentMention;
    }

    public void setCurrentMention(int currentMention) {
        this.currentMention = currentMention;
    }
}
