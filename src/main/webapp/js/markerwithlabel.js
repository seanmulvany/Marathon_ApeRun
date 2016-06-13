/**
 * @name MarkerWithLabel for V3
 * @version 1.1.5 [July 11, 2011]
 * @author Gary Little (inspired by code from Marc Ridey of Google).
 * @copyright Copyright 2010 Gary Little [gary at luxcentral.com]
 * @fileoverview MarkerWithLabel extends the Google Maps JavaScript API V3
 *  <code>google.maps.Marker</code> class.
 *  <p>
 *  MarkerWithLabel allows you to define markers with associated labels. As you would expect,
 *  if the marker is draggable, so too will be the label. In addition, a marker with a label
 *  responds to all mouse events in the same manner as a regular marker. It also fires mouse
 *  events and "property changed" events just as a regular marker would. Version 1.1 adds
 *  support for the raiseOnDrag feature introduced in API V3.3.
 *  <p>
 *  If you drag a marker by its label, you can cancel the drag and return the marker to its
 *  original position by pressing the <code>Esc</code> key. This doesn't work if you drag the marker
 *  itself because this feature is not (yet) supported in the <code>google.maps.Marker</code> class.
 */

/*!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('8 t(c,b,a){2.3=c;2.2g=c.33;2.7=T.1m("1J");2.7.4.R="11: 1B; 17: 24;";2.q=T.1m("1J");2.q.4.R=2.7.4.R;2.q.1W("2H","1z v;");2.q.1W("2s","1z v;");2.Q=t.J(b)}t.u=U 6.5.3e();t.J=8(b){s a;9(A t.J.1y==="B"){a=T.1m("2X");a.4.R="11: 1B; z-2P: 2N; L: 14;";a.4.1r="-2E";a.4.1x="-2C";a.2B=b;t.J.1y=a}1z t.J.1y};t.u.2v=8(){s g=2;s m=v;s c=v;s f;s j,1h;s p;s d;s h;s o;s n=20;s i="2l("+2.2g+")";s k=8(e){9(e.2h){e.2h()}e.3d=G;9(e.2f){e.2f()}};s l=8(){g.3.2d(35)};2.1b().2b.Y(2.7);2.1b().2Z.Y(2.q);9(A t.J.25==="B"){2.1b().2b.Y(2.Q);t.J.25=G}2.1k=[6.5.r.N(2.q,"22",8(e){9(g.3.P()||g.3.X()){2.4.16="1X";6.5.r.C(g.3,"22",e)}}),6.5.r.N(2.q,"1U",8(e){9((g.3.P()||g.3.X())&&!c){2.4.16=g.3.2J();6.5.r.C(g.3,"1U",e)}}),6.5.r.N(2.q,"1S",8(e){c=v;9(g.3.P()){m=G;2.4.16=i}9(g.3.P()||g.3.X()){6.5.r.C(g.3,"1S",e);k(e)}}),6.5.r.N(T,"1p",8(a){s b;9(m){m=v;g.q.4.16="1X";6.5.r.C(g.3,"1p",a)}9(c){9(d){b=g.Z().1v(g.3.K());b.y+=n;g.3.I(g.Z().1M(b));2A{g.3.2d(6.5.2x.2u);2t(l,2r)}2q(e){}}g.Q.4.L="14";g.3.V(f);p=G;c=v;a.H=g.3.K();6.5.r.C(g.3,"1I",a)}}),6.5.r.w(g.3.1i(),"2p",8(a){s b;9(m){9(c){a.H=U 6.5.2o(a.H.1g()-j,a.H.1f()-1h);b=g.Z().1v(a.H);9(d){g.Q.4.15=b.x+"E";g.Q.4.O=b.y+"E";g.Q.4.L="";b.y-=n}g.3.I(g.Z().1M(b));9(d){g.q.4.O=(b.y+n)+"E"}6.5.r.C(g.3,"1H",a)}W{j=a.H.1g()-g.3.K().1g();1h=a.H.1f()-g.3.K().1f();f=g.3.1d();h=g.3.K();o=g.3.1i().2n();d=g.3.F("12");c=G;g.3.V(1G);a.H=g.3.K();6.5.r.C(g.3,"1F",a)}}}),6.5.r.N(T,"2m",8(e){9(c){9(e.2k===27){d=v;g.3.I(h);g.3.1i().2j(o);6.5.r.C(T,"1p",e)}}}),6.5.r.N(2.q,"1E",8(e){9(g.3.P()||g.3.X()){9(p){p=v}W{6.5.r.C(g.3,"1E",e);k(e)}}}),6.5.r.N(2.q,"2i",8(e){9(g.3.P()||g.3.X()){6.5.r.C(g.3,"2i",e);k(e)}}),6.5.r.w(2.3,"1F",8(a){9(!c){d=2.F("12")}}),6.5.r.w(2.3,"1H",8(a){9(!c){9(d){g.I(n);g.7.4.M=1G+(2.F("1a")?-1:+1)}}}),6.5.r.w(2.3,"1I",8(a){9(!c){9(d){g.I(0)}}}),6.5.r.w(2.3,"3c",8(){g.I()}),6.5.r.w(2.3,"3b",8(){g.V()}),6.5.r.w(2.3,"3a",8(){g.19()}),6.5.r.w(2.3,"39",8(){g.19()}),6.5.r.w(2.3,"37",8(){g.1A()}),6.5.r.w(2.3,"36",8(){g.1j()}),6.5.r.w(2.3,"34",8(){g.1c()}),6.5.r.w(2.3,"32",8(){g.18()}),6.5.r.w(2.3,"31",8(){g.18()})]};t.u.30=8(){s i;2.7.29.28(2.7);2.q.29.28(2.q);1K(i=0;i<2.1k.2Y;i++){6.5.r.2W(2.1k[i])}};t.u.2V=8(){2.1j();2.1A();2.18()};t.u.1j=8(){s a=2.3.F("1l");9(A a.2U==="B"){2.7.13=a;2.q.13=2.7.13}W{2.7.13="";2.7.Y(a);a=a.2S(G);2.q.Y(a)}};t.u.1A=8(){2.q.2R=2.3.2Q()||""};t.u.18=8(){s i,D;2.7.1t=2.3.F("1s");2.q.1t=2.7.1t;2.7.4.R="";2.q.4.R="";D=2.3.F("D");1K(i 2O D){9(D.2M(i)){2.7.4[i]=D[i];2.q.4[i]=D[i]}}2.21()};t.u.21=8(){2.7.4.11="1B";2.7.4.17="24";9(A 2.7.4.S!=="B"&&2.7.4.S!==""){2.7.4.1Z="1Y(S="+(2.7.4.S*2L)+")"}2.q.4.11=2.7.4.11;2.q.4.17=2.7.4.17;2.q.4.S=0.2K;2.q.4.1Z="1Y(S=1)";2.1c();2.I();2.19()};t.u.1c=8(){s a=2.3.F("1q");2.7.4.1r=-a.x+"E";2.7.4.1x=-a.y+"E";2.q.4.1r=-a.x+"E";2.q.4.1x=-a.y+"E"};t.u.I=8(a){s b=2.Z().1v(2.3.K());9(A a==="B"){a=0}2.7.4.15=1V.1T(b.x)+"E";2.7.4.O=1V.1T(b.y-a)+"E";2.q.4.15=2.7.4.15;2.q.4.O=2.7.4.O;2.V()};t.u.V=8(){s a=(2.3.F("1a")?-1:+1);9(A 2.3.1d()==="B"){2.7.4.M=2I(2.7.4.O,10)+a;2.q.4.M=2.7.4.M}W{2.7.4.M=2.3.1d()+a;2.q.4.M=2.7.4.M}};t.u.19=8(){9(2.3.F("1u")){2.7.4.L=2.3.2G()?"2F":"14"}W{2.7.4.L="14"}2.q.4.L=2.7.4.L};8 1n(a){a=a||{};a.1l=a.1l||"";a.1q=a.1q||U 6.5.2D(0,0);a.1s=a.1s||"2T";a.D=a.D||{};a.1a=a.1a||v;9(A a.1u==="B"){a.1u=G}9(A a.12==="B"){a.12=G}9(A a.1R==="B"){a.1R=G}9(A a.23==="B"){a.23=v}9(A a.1o==="B"){a.1o=v}a.1w=a.1w||"1Q://5.1P.1O/1N/2a/26/2z.2y";a.1D=a.1D||"1Q://5.1P.1O/1N/2a/26/2w.38";a.1o=v;2.2e=U t(2,a.1w,a.1D);6.5.1e.2c(2,1L)}1n.u=U 6.5.1e();1n.u.1C=8(a){6.5.1e.u.1C.2c(2,1L);2.2e.1C(a)};',62,201,'||this|marker_|style|maps|google|labelDiv_|function|if|||||||||||||||||eventDiv_|event|var|MarkerLabel_|prototype|false|addListener||||typeof|undefined|trigger|labelStyle|px|get|true|latLng|setPosition|getSharedCross|getPosition|display|zIndex|addDomListener|top|getDraggable|crossDiv_|cssText|opacity|document|new|setZIndex|else|getClickable|appendChild|getProjection||position|raiseOnDrag|innerHTML|none|left|cursor|overflow|setStyles|setVisible|labelInBackground|getPanes|setAnchor|getZIndex|Marker|lng|lat|cLngOffset|getMap|setContent|listeners_|labelContent|createElement|MarkerWithLabel|optimized|mouseup|labelAnchor|marginLeft|labelClass|className|labelVisible|fromLatLngToDivPixel|crossImage|marginTop|crossDiv|return|setTitle|absolute|setMap|handCursor|click|dragstart|1000000|drag|dragend|div|for|arguments|fromDivPixelToLatLng|intl|com|gstatic|http|clickable|mousedown|round|mouseout|Math|setAttribute|pointer|alpha|filter||setMandatoryStyles|mouseover|draggable|hidden|processed|mapfiles||removeChild|parentNode|en_us|overlayImage|apply|setAnimation|label|stopPropagation|handCursorURL_|preventDefault|dblclick|setCenter|keyCode|url|keydown|getCenter|LatLng|mousemove|catch|1406|ondragstart|setTimeout|BOUNCE|onAdd|closedhand_8_8|Animation|png|drag_cross_67_16|try|src|9px|Point|8px|block|getVisible|onselectstart|parseInt|getCursor|01|100|hasOwnProperty|1000002|in|index|getTitle|title|cloneNode|markerLabels|nodeType|draw|removeListener|img|length|overlayMouseTarget|onRemove|labelstyle_changed|labelclass_changed|handCursorURL|labelanchor_changed|null|labelcontent_changed|title_changed|cur|labelvisible_changed|visible_changed|zindex_changed|position_changed|cancelBubble|OverlayView'.split('|'),0,{}))