package de.mlo.dev.tsbuilder.elements.imports;

/**
 * Some common imports which maybe useful
 */
public class TsImports {

    public static class Rx{
        public static final TsImport OBSERVABLE = new TsImport("Observable", "rxjs");
        public static final TsImport REPLAY_SUBJECT = new TsImport("ReplaySubject", "rxjs");
    }

    public static class Stomp{
        public static final TsImport STOMP = new TsImport("RxStomp", "@stomp/rx-stomp");
    }
}
