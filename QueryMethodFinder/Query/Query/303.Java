// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.system;

import java.lang.reflect.*;

// import sun.misc.Signal;
// import sun.misc.SignalHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class CatchSigTerm {
    /**
     * Sets up a signal handler for SIGTERM, where a given AtomicBoolean
     * gets a true value when the TERM signal is caught.
     *
     * Callers basically have two options for acting on the TERM signal:
     *
     * They may choose to synchronize and wait() on this variable,
     * and they will be notified when it changes state to true. To avoid
     * problems with spurious wakeups, use a while loop and wait()
     * again if the state is still false. As soon as the caller has been
     * woken up and the state is true, the application should exit as
     * soon as possible.
     *
     * They may also choose to poll the state of this variable. As soon
     * as its state becomes true, the signal has been received, and the
     * application should exit as soon as possible.
     *
     * @param signalCaught set to false initially, will be set to true when SIGTERM is caught.
     */
    @SuppressWarnings("rawtypes")
    public static void setup(final AtomicBoolean signalCaught) {
        signalCaught.set(false);
        try {
            Class shc = Class.forName("sun.misc.SignalHandler");
            Class ssc = Class.forName("sun.misc.Signal");

            InvocationHandler ihandler = new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        synchronized (signalCaught) {
                            signalCaught.set(true);
                            signalCaught.notifyAll();
                        }
                        return null;
                    }
                };
            Object shandler = Proxy.newProxyInstance(CatchSigTerm.class.getClassLoader(),
                    new Class[] { shc },
                    ihandler);
            Constructor[] c = ssc.getDeclaredConstructors();
            assert c.length == 1;
            Object sigterm = c[0].newInstance("TERM");
            Method m = findMethod(ssc, "handle");
            assert m != null; // "NoSuchMethodException"
            m.invoke(null, sigterm, shandler);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("FAILED setting up signal catching: "+e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static Method findMethod(Class c, String name) {
        for (Method m : c.getDeclaredMethods()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }
}
