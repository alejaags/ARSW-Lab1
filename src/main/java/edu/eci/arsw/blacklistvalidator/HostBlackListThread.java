/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2110111
 */
public class HostBlackListThread extends Thread{
    int oC = 0;

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private String ipaddress;
    private int N;
    private List<Integer> blackListOcurrences;
    
    public HostBlackListThread(String ipaddress, int N){
        this.ipaddress = ipaddress;
        this.N = N;
        blackListOcurrences= checkHost(ipaddress, N);
        //System.out.println(Ocurrences());
       
    }
    
    public List<Integer> checkHost(String ipaddress, int N){
        
        LinkedList<HostBlackListThreadParts> hbltp =new LinkedList<>();
        
    
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=0;
        int partes = skds.getRegisteredServersCount()/ N;
        System.out.println(partes);
        int []part = new int [N+1];
        for(int i = 0; i <= N; i++){
            part[i]=i*partes;
        }
        System.out.println(Arrays.toString(part));
        for (int i=1; i < part.length; i++){
            HostBlackListThreadParts t = new HostBlackListThreadParts("202.24.34.55",part[i-1],part[i]-1);
            hbltp.add(t);
        }
        for (int i=0; i < part.length-1; i++){
            hbltp.get(i).start();
        }
        return blackListOcurrences;
    }
    

 
    
}
