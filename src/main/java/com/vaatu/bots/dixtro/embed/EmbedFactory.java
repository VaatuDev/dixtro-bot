package com.vaatu.bots.dixtro.embed;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.util.List;

public class EmbedFactory {
    private static String getVideoLength(Long videoLength) {
        long parsedLongToDouble = videoLength / 60000;
        int nearestInteger = (int) Math.floor(parsedLongToDouble);
        String minutes = String.valueOf(nearestInteger);
        return minutes + " minutes";
    }

    private static String shortenTrackTitle(String title) {
        if (title.length() > 40) {
            return title.substring(0, 40) + "...";
        } else {
            return title;
        }
    }

    public static MessageEmbed createSongEmbed(AudioTrackInfo trackInfo) {
        EmbedBuilder newEmbed = new EmbedBuilder();
        newEmbed.setTitle("💿 " + trackInfo.title);
        newEmbed.setAuthor("🖋️ Author: " + trackInfo.author);
        newEmbed.addField("🎵 Video Length:", getVideoLength(trackInfo.length), false);
        newEmbed.setImage(trackInfo.artworkUrl);
        newEmbed.setColor(Color.ORANGE);
        return newEmbed.build();
    }

    public static MessageEmbed createDefault(String message) {
        EmbedBuilder newEmbed = new EmbedBuilder();
        newEmbed.setTitle(message);
        newEmbed.setColor(Color.ORANGE);
        return newEmbed.build();
    }

    public static MessageEmbed createUserErrorEmbed(String errorReason) {
        EmbedBuilder newEmbed = new EmbedBuilder();
        newEmbed.setTitle("User Input Error");
        newEmbed.addField("Reason:", errorReason, true);
        newEmbed.setColor(Color.RED);
        return newEmbed.build();
    }

    public static MessageEmbed createQueueEmbed(List<AudioTrack> tracks) {
        EmbedBuilder newEmbed = new EmbedBuilder();
        newEmbed.setColor(Color.ORANGE);
        newEmbed.setTitle("Tracks queue");
        newEmbed.setFooter("Queue size: " + tracks.size() + " songs.");
        tracks = tracks.size() >= 5 ? tracks.subList(0, 4) : tracks;

        AudioTrackInfo actualTrackInfo = tracks.removeFirst().getInfo();
        newEmbed.addField("⏸️ " + actualTrackInfo.author, actualTrackInfo.title, false);

        for (AudioTrack track : tracks) {
            AudioTrackInfo trackInfo = track.getInfo();
            String trackTitle = shortenTrackTitle(trackInfo.title);
            newEmbed.addField("▶️ Author: " + trackInfo.author, trackTitle, false);
        }

        return newEmbed.build();
    }

    public static MessageEmbed createInternalErrorEmbed() {
        EmbedBuilder newEmbed = new EmbedBuilder();
        newEmbed.setTitle("Internal Error.");
        newEmbed.addField("Suggestion", "report this whenever you can.", true);
        newEmbed.setColor(Color.RED);
        return newEmbed.build();
    }
}
