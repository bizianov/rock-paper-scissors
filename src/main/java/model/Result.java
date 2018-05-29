package model;

/**
 * Each turn (or round) has it's own result.
 * Result is evaluated from the perspective of player (not the server).
 * So, WIN is WIN for player and LOSS for the server.
 */
public enum Result {
    WIN, LOSS, DRAW
}