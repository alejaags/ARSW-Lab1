/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2110111
 */
public class HostBlackListThreadParts extends Thread{
    int oC = 0;

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    private String ipAddress;
    private int vi,vf;
    private List<Integer> blackListOcurrences;

    @Override
    public void run() {
        blackListOcurrences = checkHost(ipAddress, vi,vf);
         System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        Ocurrences();
    }
    
    

    public HostBlackListThreadParts(String ipaddress, int vi, int vf) {
        ipAddress = ipaddress;
        this.vi = vi;
        this.vf = vf;
    }
    
    
    public List<Integer> checkHost(String ipaddress, int vi, int vf){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
        int ocurrencesCount=0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=vi;
       
        for (int i=vi;i< vf && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
            checkedListsCount++;
            
            if (skds.isInBlackListServer(i, ipaddress)){
                
                blackListOcurrences.add(i);
                
                ocurrencesCount++;
                oC = ocurrencesCount;
            }
        }
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    public void Ocurrences(){
        System.out.printf("%d",oC);
    }
}
