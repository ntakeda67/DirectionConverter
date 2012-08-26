@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2')
import groovyx.net.http.HTTPBuilder
import groovy.json.*
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON

class CoordCalculator{
  def urlBase = 'http://maps.googleapis.com'
  def path = [direction:'/maps/api/directions/json',
	      geocoding:'/maps/api/geocode/json'];
  
  def waypoints = [];

  def query = [mode:'driving', sensor:'false', region:'ja', language:'ja', origin:'箕面市', destination:'千里中央'];

  def parser = new JsonSlurper();

  def makeUrl(){
    def url = urlBase + path['direction'] + '?';
    def params = [];
    query.collect { params << 
	it.key + '=' + it.value;
    };

    return url + params.join('&');
  }

  def latitude(){
  }
  
  def longitude(){
  }

  def execute(){ 
    def http = new groovyx.net.http.HTTPBuilder(urlBase);
    def direction =[:];
    
    http.get(path:path['direction'], query:query){ resp, json ->
      println resp.status
      json.each{ 
	println it.key + ' : ' + it.value
      }
      direction = parser.parseText(json);
    }

    return direction;
    /*
    http.request(GET, JSON) { req ->
      uri.path = path['direction']
      uri.query = query
      
      response.success = { resp, json ->
	println "Query response: "
	json.each {
	  println "${it.titleNoFormatting} : ${it.visibleUrl}"
	}
      }

      response.'404' = {
	println 'NOT FOUND'
      }
    }
    */
   }
};


CoordCalculator calc = new CoordCalculator();
println calc.execute();