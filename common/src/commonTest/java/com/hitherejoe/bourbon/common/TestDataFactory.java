package com.hitherejoe.bourbon.common;

import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.data.model.Image;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    private static final Random sRandom = new Random();

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt() {
        return sRandom.nextInt();
    }

    public static Shot makeShot(int id) {
        Shot shot = new Shot();
        shot.id = id;
        shot.title = randomUuid();
        shot.description = randomUuid();
        shot.image = randomUuid();
        shot.likes_count = String.valueOf(sRandom.nextInt(999));
        shot.views_count = String.valueOf(sRandom.nextInt(999));
        shot.images = new Image();
        shot.images.hidpi = randomUuid();
        shot.images.normal = randomUuid();
        shot.images.teaser = randomUuid();
        shot.user = makeUser(0);
        return shot;
    }

    public static List<Shot> makeShots(int count) {
        List<Shot> shots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            shots.add(makeShot(i));
        }
        return shots;
    }

    public static Comment makeComment(int id) {
        Comment comment = new Comment();
        comment.id = sRandom.nextInt();
        comment.body = randomUuid();
        comment.user = makeUser(sRandom.nextInt());
        return comment;
    }

    public static List<Comment> makeComments(int count) {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            comments.add(makeComment(i));
        }
        return comments;
    }

    public static User makeUser(int id) {
        User user = new User();
        user.id = id;
        user.name = randomUuid();
        user.htmlUrl = randomUuid();
        user.avatarUrl = randomUuid();
        user.username = randomUuid();
        return user;
    }
}