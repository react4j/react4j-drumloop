package react4j.drumloop.model;

import arez.component.CollectionsUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class TrackRepository
{
  @Nonnull
  private final ArrayList<Track> _tracks = new ArrayList<>();

  @Inject
  TrackRepository()
  {
    _tracks.add( new Track( "Kick", "sounds/kick.wav" ) );
    _tracks.add( new Track( "Sub1", "sounds/bass.wav" ) );
    _tracks.add( new Track( "Sub2", "sounds/sub.wav" ) );
    _tracks.add( new Track( "Snare", "sounds/snare.wav" ) );
    _tracks.add( new Track( "Clap", "sounds/clap.wav" ) );
    _tracks.add( new Track( "HiHat", "sounds/hat2.wav" ) );
    _tracks.add( new Track( "OpenHiHat", "sounds/openhihat.wav" ) );
  }

  @Nonnull
  public List<Track> getTracks()
  {
    return CollectionsUtil.wrap( _tracks );
  }
}
