package tech.bongers.aoc.aoc2015.day;

import tech.bongers.aoc.aoc2015.Year2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <a href="https://adventofcode.com/2015/day/4>2015 Day 04</a>
 */
public class Day04 extends Year2015 {

    public static void main(final String[] args) {
        new Day04().doPuzzle();
    }

    @Override
    public void doPuzzle() {
        findSmallestNumberWithLeadingZeros("00000");
        findSmallestNumberWithLeadingZeros("000000");
    }

    private void findSmallestNumberWithLeadingZeros(final String startWithLeadingZeros) {
        final String secret = "ckczppom";
        boolean hashFound = false;
        int counter = 0;

        while (!hashFound) {
            final String hash = md5Hash(secret + counter);
            if (hash.startsWith(startWithLeadingZeros)) {
                log("Hash found: {}. Number: {}", hash, counter);
                hashFound = true;
            }
            counter++;
        }
    }

    private String md5Hash(final String secret) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            final byte[] hashInBytes = messageDigest.digest(secret.getBytes());
            final StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashInBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            log("Error while hashing: {}", e.getMessage());
            return "";
        }
    }
}
