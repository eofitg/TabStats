package club.maxstats.tabstats.playerapi.exception;

public class NonWhitelistedKeyException extends Exception {
    public NonWhitelistedKeyException() {
        System.out.println("IP address not whitelisted for this key.");
    }
}
