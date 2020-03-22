package org.matsim.run.drt;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.ControlerUtils;
import org.matsim.core.controler.OutputDirectoryLogging;
import org.matsim.core.events.EventsUtils;
import org.matsim.run.drt.EpisimConfigGroup.FacilitiesHandling;

import java.io.IOException;
import java.util.Arrays;

class KNEventsInfection2{

        public static void main( String[] args ) throws IOException{
                OutputDirectoryLogging.catchLogEntries();

                Config config = ConfigUtils.createConfig( new EpisimConfigGroup() );
                EpisimConfigGroup episimConfig = ConfigUtils.addOrGetModule( config, EpisimConfigGroup.class );

//                episimConfig.setInputEventsFile( "https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.4-1pct/output-berlin-v5.4-1pct/berlin-v5.4-1pct.output_events_wo_linkEnterLeave.xml.gz" );
                episimConfig.setInputEventsFile( "../public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.4-1pct/output-berlin-v5.4-1pct/berlin-v5.4-1pct.output_events_for_episim.xml.gz" );
                episimConfig.setFacilitiesHandling( FacilitiesHandling.bln );
//                episimConfig.setSample(0.01);
                episimConfig.setCalibrationParameter(2);

//                config.controler().setOutputDirectory( "output-base" );

//                episimConfig.setUsePt( EpisimConfigGroup.UsePt.no );
//                episimConfig.setUsePtDate( 10. );
//                config.controler().setOutputDirectory( "output-wo-pt-from-it10" );

                int closingIteration = 10;
//                episimConfig.setUsePtDate( closingIteration );
//                episimConfig.setUsePt( EpisimConfigGroup.UsePt.no );
                episimConfig.setShutdownDate( closingIteration );
//                config.controler().setOutputDirectory( "output-wpti2-w20pct-leisure00pct-it" + closingIteration );
                config.controler().setOutputDirectory( "abc" + closingIteration );

//                int closingIteration = 30 ;
//                episimConfig.setClosedActivity1( "work" );
//                episimConfig.setClosedActivity2( "leisure" );
//                episimConfig.setClosedActivity1Date( closingIteration );
//                episimConfig.setClosedActivity2Date( closingIteration );
//                config.controler().setOutputDirectory( "output-shutdown-work-leisure-from-it" + closingIteration );

//                int closingIteration = 30 ;
//                episimConfig.setClosedActivity1( "work" );
//                episimConfig.setClosedActivity2( "leisure" );
//                episimConfig.setClosedActivity1Date( closingIteration );
//                episimConfig.setClosedActivity2Date( closingIteration );
//                episimConfig.setUsePt( EpisimConfigGroup.UsePt.no );
//                episimConfig.setUsePtDate( closingIteration );
//                config.controler().setOutputDirectory( "output-shutdown-work-leisure-pt-from-it" + closingIteration );

                ConfigUtils.applyCommandline( config, Arrays.copyOfRange( args, 0, args.length ) ) ;

                OutputDirectoryLogging.initLoggingWithOutputDirectory( config.controler().getOutputDirectory() );

                EventsManager events = EventsUtils.createEventsManager();

                events.addHandler( new InfectionEventHandler( config ) );

                ControlerUtils.checkConfigConsistencyAndWriteToLog(config, "Just before starting iterations");
                for ( int iteration=0 ; iteration<=100 ; iteration++ ){
                        events.resetHandlers( iteration );
                        EventsUtils.readEvents( events, episimConfig.getInputEventsFile() );
                }

                OutputDirectoryLogging.closeOutputDirLogging();

        }

}
