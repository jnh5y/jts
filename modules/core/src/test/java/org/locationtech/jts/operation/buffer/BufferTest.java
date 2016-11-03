
/*
 * Copyright (c) 2016 Vivid Solutions.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.locationtech.jts.operation.buffer;

import org.locationtech.jts.geom.PrecisionModel;

import junit.framework.TestCase;



/**
 * @version 1.7
 */
public class BufferTest extends TestCase {

  public BufferTest(String name) {
    super(name);
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(BufferTest.class);
  }

  public void testFirst() throws Exception
  {
    testMultiLineString_separateBuffers_floatingSingle();
  }
  
  public void testMultiLineString_depthFailure() throws Exception {
    new BufferValidator(
      15,
      "MULTILINESTRING ((1335558.59524 631743.01449, 1335572.28215 631775.89056, 1335573.2578018496 631782.1915185435),  (1335573.2578018496 631782.1915185435, 1335576.62035 631803.90754), (1335558.59524 631743.01449, 1335573.2578018496 631782.1915185435), (1335573.2578018496 631782.1915185435, 1335580.70187 631802.08139))")
      .setEmptyBufferExpected(false)
      .test();
  }
  public void testMultiLineString_separateBuffers_floating() throws Exception {
    new BufferValidator(
      0.01,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))")
      .setBufferHolesExpected(false)
      .setEmptyBufferExpected(false)
      .test();
  }
  public void testMultiLineString2_buffersTouchToMakeHole_floating() throws Exception {
    new BufferValidator(
      0.037,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))")
      .setBufferHolesExpected(true)
      .setEmptyBufferExpected(false)
      .test();
  }
  public void testMultiLineString3_holeVanishes_floating() throws Exception {
    new BufferValidator(
      0.16,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))")
      .setBufferHolesExpected(false)
      .setEmptyBufferExpected(false)
      .test();
  }
  public void testMultiLineString4_reallyBigDistance_floating() throws Exception {
    new BufferValidator(
      1E10,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))")
      .setBufferHolesExpected(false)
      .setEmptyBufferExpected(false)
      .test();
  }

  public void testMultiLineString_separateBuffers_floatingSingle() throws Exception {
    BufferValidator bv = new BufferValidator(
      0.01,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))",
      false);
    
      bv.setBufferHolesExpected(false);
      bv.setEmptyBufferExpected(true);
      bv.setPrecisionModel(new PrecisionModel(PrecisionModel.FLOATING_SINGLE));
      bv.test();
  }
  
  public void testMultiLineString2_buffersTouchToMakeHole_floatingSingle() throws Exception {
    new BufferValidator(
      0.037,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))",
      false)
      .setBufferHolesExpected(false)
      .setEmptyBufferExpected(true)
      .setPrecisionModel(new PrecisionModel(PrecisionModel.FLOATING_SINGLE))
      .test();
  }
  public void testMultiLineString3_holeVanishes_floatingSingle() throws Exception {
    new BufferValidator(
      0.16,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))",
      false)
      .setBufferHolesExpected(false)
      .setEmptyBufferExpected(true)
      .setPrecisionModel(new PrecisionModel(PrecisionModel.FLOATING_SINGLE))
      .test();
  }
  public void testMultiLineString4_reallyBigDistance_floatingSingle() throws Exception {
    new BufferValidator(
      1E10,
      "MULTILINESTRING (( 635074.5418406526 6184832.4888257105, 635074.5681951842 6184832.571842485, 635074.6472587794 6184832.575795664 ), ( 635074.6657069515 6184832.53889932, 635074.6933792098 6184832.451929366, 635074.5642420045 6184832.474330718 ))")
      .setBufferHolesExpected(false)
      .setEmptyBufferExpected(false)
      .setPrecisionModel(new PrecisionModel(PrecisionModel.FLOATING_SINGLE))
      .test();
  }
  public void testPolygon_MultipleHoles() throws Exception {
    new BufferValidator(
      10.0,
      "POLYGON (( 78 82, 78 282, 312 282, 312 82, 78 82 ), ( 117 242, 122 242, 122 248, 117 248, 117 242 ), ( 156 104, 288 104, 288 210, 156 210, 156 104 ))")
      .setBufferHolesExpected(true)
      .setEmptyBufferExpected(false)
      .setPrecisionModel(new PrecisionModel(PrecisionModel.FLOATING))
      .test();
  }

  public void test1() throws Exception {
    new BufferValidator(
      0,
      "POINT (100 100)")
      .setEmptyBufferExpected(true)
      .test();
  }
  public void test2() throws Exception {
    new BufferValidator(
      0,
      "LINESTRING (10 10, 100 100)")
      .setEmptyBufferExpected(true)
      .test();
  }
  public void test1a() throws Exception {
    new BufferValidator(
      -1,
      "POINT (100 100)")
      .setEmptyBufferExpected(true)
      .test();
  }
  public void test2a() throws Exception {
    new BufferValidator(
      -1,
      "LINESTRING (10 10, 100 100)")
      .setEmptyBufferExpected(true)
      .test();
  }

  public void test3() throws Exception {
    new BufferValidator(
      10,
      "LINESTRING (100 100, 200 100, 200 200, 100 200, 100 100)")
      .test();
  }

  public void test4() throws Exception {
    new BufferValidator(
      50,
      "LINESTRING (40 40, 160 40, 100 180, 40 80)")
      .test();
  }
  public void test5() throws Exception {
    new BufferValidator(
      0,
      "POLYGON ((80 300, 280 300, 280 300, 280 300, 280 80, 80 80, 80 300))")
      .test();
  }
  public void test6() throws Exception {
    new BufferValidator(
      10,
      "POLYGON ((60 300, 60 160, 240 160, 240 300, 60 300))")
      .test();
  }
  public void test7() throws Exception {
    new BufferValidator(
      10,
      "POLYGON ((80 300, 280 300, 280 80, 80 80, 80 300), (260 280, 180 200, 100 280, 100 100, 260 100, 260 280))")
      .test();
  }
  public void test8() throws Exception {
    new BufferValidator(
      200,
      "POLYGON ((80 300, 280 300, 280 80, 80 80, 80 300), (260 280, 180 200, 100 280, 100 100, 260 100, 260 280))")
      .test();
  }
  public void test9() throws Exception {
    new BufferValidator(
      -10,
      "POLYGON ((80 300, 280 300, 280 80, 80 80, 80 300))")
      .test();
  }
  public void test10() throws Exception {
    new BufferValidator(
      10,
      "POLYGON ((100 300, 300 300, 300 100, 100 100, 100 300), (220 220, 180 220, 180 180, 220 180, 220 220))")
      .test();
  }
  public void test11() throws Exception {
    new BufferValidator(
      5,
      "POLYGON ((260 400, 220 300, 80 300, 180 220, 40 200, 180 160, 60 20, 200 80, 280 20, 260 140, 440 20, 340 180, 520 160, 280 220, 460 340, 300 300, 260 400), (260 320, 240 260, 220 220, 160 180, 220 160, 200 100, 260 160, 300 140, 320 180, 260 200, 260 320))")
      .test();
  }
  public void test12() throws Exception {
    new BufferValidator(
      -17,
      "POLYGON ((260 320, 240 260, 220 220, 160 180, 220 160, 260 160, 260 200, 260 320))")
      .test();
  }
  public void test13() throws Exception {
    new BufferValidator(
      -17,
      "POLYGON ((260 320, 240 260, 220 220, 260 160, 260 320))")
      .test();
  }
  public void test14() throws Exception {
    new BufferValidator(
      -14,
      "POLYGON ((260 320, 240 260, 220 220, 260 160, 260 320))")
      .test();
  }
  public void test15() throws Exception {
    new BufferValidator(
      26,
      "LINESTRING (260 160, 260 200, 260 320, 240 260, 220 220)")
      .test();
  }
  public void test16() throws Exception {
    new BufferValidator(
      -7,
      "POLYGON ((260 400, 220 300, 80 300, 180 220, 40 200, 180 160, 60 20, 200 80, 280 20, 260 140, 440 20, 340 180, 520 160, 280 220, 460 340, 300 300, 260 400), (260 320, 240 260, 220 220, 160 180, 220 160, 200 100, 260 160, 300 140, 320 180, 260 200, 260 320))")
      .test();
  }
  public void test17() throws Exception {
    new BufferValidator(
      -183,
      "POLYGON ((32 136, 27 163, 30 236, 34 252, 49 291, 72 326, 83 339, 116 369, 155 391, 176 400, 219 414, 264 417, 279 416, 339 401, 353 395, 380 381, 394 372, 441 328, 458 303, 463 294, 480 251, 486 205, 486 183, 473 115, 469 105, 460 85, 454 74, 423 33, 382 2, 373 -3, 336 -19, 319 -24, 275 -31, 252 -32, 203 -27, 190 -24, 149 -10, 139 -5, 84 37, 76 46, 52 81, 36 121, 32 136))")
      .test();
  }
  public void test18() throws Exception {
    new BufferValidator(
      20,
      "POLYGON((-4 225, -17 221, -16 223, -15 224, -13 227, -4 225))")
      .test();
  }
  public void test19() throws Exception {
    new BufferValidator(
      21,
      "POLYGON ((184 369, 181 368, 180 368, 179 367, 176 366, 185 357, 184 369 ))")
      .test();
  }
  public void test20() throws Exception {
    new BufferValidator(
      1000,
      "POLYGON ((13841 1031, 13851 903, 13853 885, 13853 875, 13856 862, 13859 831, 13670 900, 13841 1031))")
      .test();
  }
  public void test21() throws Exception {
    new BufferValidator(
      18,
      "POLYGON ((164 84, 185 91, 190 75, 187 76, 182 77, 179 79, 178 79, 174 81, 173 81, 172 82, 169 83,  164 84 ))")
      .test();
  }
  public void test22() throws Exception {
    new BufferValidator(
      15,
      "POLYGON ((224 271, 225 261, 214 258, 210 266, 212 267, 214 267, 217 268, 218 268, 219 268, 221 269, 222 270,  224 271 ))")
      .test();
  }
  public void test23() throws Exception {
    new BufferValidator(
      25,
      "POLYGON ((484 76, 474 79, 492 122, 502 119, 501 117, 500 112, 499 111, 498 107, 497 104, 496 103, 494 98, 493 96, 491 92, 490 90, 489 86, 487 81, 486 79, 485 77, 484 76 ))")
      .test();
  }
  public void test24() throws Exception {
    new BufferValidator(
      160,
      "POLYGON ((20 60, 20 20, 240 20, 40 21, 240 22, 40 22, 240 23, 240 60, 20 60))")
      .test();
  }
  public void test25() throws Exception {
    new BufferValidator(
      -3,
      "POLYGON ((233 195, 232 195, 231 194, 222 188, 226 187, 227 187, 229 187, 230 186, 232 186, 234 185, 236 184, 237 183, 238 182, 237 184, 236 185, 236 186, 235 187, 235 188, 234 189, 234 191, 234 192, 233 193, 233 195 ))")
      .test();
  }
  public void test26() throws Exception {
    new BufferValidator(
      6,
      "LINESTRING (233 195, 232 195, 231 194, 222 188, 226 187, 227 187, 229 187, 230 186, 232 186, 234 185, 236 184, 237 183, 238 182, 237 184, 236 185, 236 186, 235 187, 235 188, 234 189, 234 191, 234 192, 233 193, 233 195 )")
      .test();
  }
  public void test27() throws Exception {
    new BufferValidator(
      -30,
      "POLYGON ((2330 1950, 2320 1950, 2310 1940, 2220 1880, 2260 1870, 2270 1870, 2290 1870, 2300 1860, 2320 1860, 2340 1850, 2360 1840, 2370 1830, 2380 1820, 2370 1840, 2360 1850, 2360 1860, 2350 1870, 2350 1880, 2340 1890, 2340 1910, 2340 1920, 2330 1930, 2330 1950 ))")
      .test();
  }
  public void test28() throws Exception {
    new BufferValidator(
      30,
      "LINESTRING (2330 1950, 2320 1950, 2310 1940, 2220 1880, 2260 1870, 2270 1870, 2290 1870, 2300 1860, 2320 1860, 2340 1850, 2360 1840, 2370 1830, 2380 1820, 2370 1840, 2360 1850, 2360 1860, 2350 1870, 2350 1880, 2340 1890, 2340 1910, 2340 1920, 2330 1930, 2330 1950 )")
      .test();
  }
  public void test29() throws Exception {
    new BufferValidator(
      26,
      "POLYGON ((440 -93, 440 -67, 475 -67, 471 -71, 469 -72, 468 -73, 467 -74, 463 -78, 459 -81, 458 -82, 454 -84, 453 -85, 452 -86, 450 -86, 449 -87, 448 -88, 444 -90, 443 -91, 441 -92, 440 -93 ))")
      .test();
  }
  public void test30() throws Exception {
    new BufferValidator(
      260,
      "POLYGON ((4400 -930, 4400 -670, 4750 -670, 4710 -710, 4690 -720, 4680 -730, 4670 -740, 4630 -780, 4590 -810, 4580 -820, 4540 -840, 4530 -850, 4520 -860, 4500 -860, 4490 -870, 4480 -880, 4440 -900, 4430 -910, 4410 -920, 4400 -930 ))")
      .test();
  }
  public void test31() throws Exception {
    new BufferValidator(
      0.1,
      "POLYGON ((635074.6769928858 6184832.427381967, 635075.6723193424 6184799.950949265, 634717.5983159657 6184655.107092909, 634701.0176852546 6184648.498845058, 634697.7188197445 6184647.20632975, 634694.416887708 6184645.922033237, 634691.1138635761 6184644.642692243, 634687.8077729489 6184643.371570057, 634684.498667351 6184642.107006015, 634681.1875340013 6184640.847368483, 634677.8742698929 6184639.595978798, 634674.5570551592 6184638.351118257, 634671.2386969016 6184637.112873929, 634667.9173237421 6184635.881187774, 634664.5938713895 6184634.656088823, 634661.2674041622 6184633.437548058, 634657.9388577675 6184632.2255945075, 634654.6082322216 6184631.02022817, 634651.2745403448 6184629.823080709, 634647.9388208436 6184628.630859804, 634644.6000865338 6184627.4451971175, 634641.2592216335 6184626.267782336, 634637.9163291481 6184625.095294129, 634634.5713061031 6184623.931053837, 634631.2232683088 6184622.773371783, 634636.1918816608 6184608.365992378, 634633.2495506873 6184607.353869728, 634630.3051410569 6184606.348333739, 634627.3587557608 6184605.346063082, 634624.4102918282 6184604.3503790945, 634621.4607364619 6184603.359650123, 634618.5091539674 6184602.37384716, 634615.5564800596 6184601.392999219, 634612.6017790422 6184600.417077295, 634609.6450509242 6184599.446081388, 634606.6862442375 6184598.481672177, 634603.7263976521 6184597.52055733, 634600.7654082242 6184596.566058185, 634597.80145603 6184595.61645607, 634594.8364124894 6184594.671808995, 634591.8702261405 6184593.733777636, 634588.9020642313 6184592.799011653, 634585.9318238292 6184591.870832384, 634582.960543591 6184590.945947501, 634579.9871848791 6184590.027649342, 634577.0127348808 6184589.11430624, 634574.0362578988 6184588.205889201, 634571.0586381858 6184587.304087893, 634568.0790429743 6184586.405551985, 634565.0983050519 6184585.513631817, 634562.115540186 6184584.626637723, 634559.1316840936 6184583.744598703, 634556.1458010782 6184582.867485766, 634553.1587753976 6184581.996988578, 634550.1697742692 6184581.12975681, 634547.1796305005 6184580.269140797, 634544.1874598458 6184579.41345088, 634541.194198027 6184578.562716054, 634538.1998450506 6184577.716936321, 634535.2034137691 6184576.877743362, 634532.2059428038 6184576.041844833, 634529.2063935531 6184575.212533085, 634526.2057531879 6184574.388176442, 634523.2040217179 6184573.568774906, 634520.2002119992 6184572.755960159, 634517.1953626422 6184571.946439856, 634514.1893707667 6184571.143535337, 634510.267712847 6184585.871039091, 634281.9449709259 6184525.076957544, 633860.4859191478 6184412.861324424, 633664.3557212166 6184360.639468017, 633645.5884675509 6184355.641948889, 633486.222 6184313.208, 633485.7474265156 6184328.852301474, 633485.2749953512 6184344.496113185, 633650.4562371405 6184388.478170839, 633669.5206846121 6184393.553017912, 633852.6461183216 6184442.312440121, 634280.9949861752 6184556.364455, 634502.4254528129 6184615.324425217, 634505.716566367 6184616.204307566, 634509.0065372197 6184617.090806118, 634512.2953653594 6184617.983920872, 634515.5812308139 6184618.88193318, 634518.8659020835 6184619.788222348, 634522.1484948951 6184620.7010987215, 634525.4299963829 6184621.61893061, 634528.7093679372 6184622.545010364, 634531.9867124417 6184623.476016638, 634535.2619784358 6184624.413610098, 634538.5360501477 6184625.3594804, 634541.807159074 6184626.310248219, 634545.0771251463 6184627.267632202, 634548.3459483465 6184628.231632348, 634551.611808724 6184629.200529998, 634554.8764747474 6184630.177704472, 634558.138126462 6184631.161437107, 634561.3986867118 6184632.150125222, 634564.6571168633 6184633.147061154, 634567.9135198268 6184634.14892356, 634571.1678441261 6184635.157373106, 634574.421025444 6184636.172438785, 634577.6711923747 6184637.194062597, 634580.9192805979 6184638.222273537, 634584.1662257991 6184639.257100599, 634587.410208043 6184640.296825108, 634590.6529957657 6184641.3448264, 634593.8928205046 6184642.397725132, 634597.131502168 6184643.457239971, 634600.3671178726 6184644.524973575, 634603.6016934284 6184645.596001944, 634605.6958877691 6184646.2958191885, 634606.946276627 6184646.713661825, 634608.6177147877 6184647.275847967, 634610.2887808911 6184647.8379089665, 634613.6292576884 6184648.967082693, 634616.9666683461 6184650.104475331, 634620.3029357173 6184651.248484221, 634623.6361369188 6184652.4007120095, 634626.9663749042 6184653.557837355, 634630.2954695637 6184654.721578941, 634633.6214980055 6184655.893539405, 634636.9454988878 6184657.070426424, 634640.2664335228 6184658.255532312, 634643.5852890809 6184659.44722541, 634646.9020655481 6184660.6455057105, 634650.2167629072 6184661.850373212, 634653.5284454564 6184663.061798892, 634656.8370616816 6184664.2814434115, 634660.1436502574 6184665.506014449, 634663.4481081718 6184666.7388333315, 634666.7496027217 6184667.976549702, 634670.0489665787 6184669.222513908, 634673.3453155413 6184670.475036258, 634676.6396367943 6184671.732485097, 634679.9308916207 6184672.9981527375, 634683.2200672035 6184674.270407524, 634686.5062278372 6184675.54922043, 634689.789322 6184676.8362521175, 634693.0703883899 6184678.128210268, 634696.3493754807 6184679.426755545, 634699.6253475712 6184680.731858918, 634702.8982531315 6184682.04518105, 634706.1681951834 6184683.363400595, 635074.6769928858 6184832.427381967))")
      .test();
  }
  public void test32() throws Exception {
    new BufferValidator(
      30,
      "MULTILINESTRING ((80 285, 85.5939933259177 234.65406006674084 ), (85.5939933259177 234.65406006674084, 98 123, 294 92, 344.3694502052736 126.0884157954882 ), (344.3694502052736 126.0884157954882, 393 159 ), (51 235, 85.5939933259177 234.65406006674084 ), (85.5939933259177 234.65406006674084, 251 233, 344.3694502052736 126.0884157954882 ), (344.3694502052736 126.0884157954882, 382 83 ))")
      .test();
  }
  public void test33() throws Exception {
    //Get side location conflict in #contains, but the geometry is invalid
    // [Jon Aquino 10/29/2003]
    //    new BufferValidator(.0001, new
	// PrecisionModel(PrecisionModel.FLOATING_SINGLE), "MULTIPOLYGON
	// (((708258.754920656
    // 2402197.91172757, 708257.029447455 2402206.56901508, 708652.961095455
    // 2402312.65463437, 708657.068786251 2402304.6356364, 708258.754920656
    // 2402197.91172757 )), ((708653.498611049 2402311.54647056,
    // 708708.895756966 2402203.47250014, 708280.326454234 2402089.6337791,
    // 708247.896591321 2402252.48269854, 708367.379593851 2402324.00761653,
    // 708248.882609455 2402253.07294874, 708249.523621829 2402244.3124463,
    // 708261.854734465 2402182.39086576, 708262.818392579 2402183.35452387,
    // 708653.498611049 2402311.54647056 )))")
    //      .test();
  }
  public void test34() throws Exception {
    new BufferValidator(
      1,
      "GEOMETRYCOLLECTION (POLYGON ((0 10, 10 0, 10 10, 0 10),  (4 8, 8 4, 8 8, 4 8)),   LINESTRING (6 6, 20 20))")
      .test();
  }
  public void test35() throws Exception {
    new BufferValidator(
      20,
      "GEOMETRYCOLLECTION (POINT (100 100), POLYGON ((400 260, 280 380, 240 220, 120 300, 120 100, 260 40, 200 160, 400 260)), LINESTRING (260 400, 220 280, 120 400, 20 280, 160 160, 60 40, 160 20, 360 140))")
      .test();
  }
  public void test36() throws Exception {
    new BufferValidator(
      20,
      "GEOMETRYCOLLECTION (POINT (100 100), POLYGON ((400 260, 120 300, 120 100, 400 260)), LINESTRING (20 280, 160 160, 60 40))")
      .test();
  }
  public void test37() throws Exception {
    new BufferValidator(
      300,
      "POLYGON ((-140 700, 880 1120, 1280 -120, 300 -600, -480 -480, -140 700),   (0 360, 780 500, 240 -220, 0 360))")
      .test();
  }
  public void test38() throws Exception {
    new BufferValidator(
      300,
      "POLYGON ((-140 700, 880 1120, 1280 -120, 300 -600, -480 -480, -140 700),   (0 360, 240 -220, 780 500, 0 360))")
      .test();
  }
  public void test39() throws Exception {
    new BufferValidator(
      30,
      "MULTIPOLYGON (((0 400, 440 400, 440 0, 0 0, 0 400),(380 360, 160 120, 260 80, 380 360)), ((360 320, 200 120, 240 100, 360 320)))")
      .test();
  }
  /**
   * The #testFloatingPrecisionN tests were taken from bufferError-dist
   * 100.jml.
   */
  public void testFloatingPrecision1() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (331771 5530174, 331776 5530175, 331782 5530177, 331787 5530177, 331791 5530178, 331796 5530178, 331800 5530178, 331805 5530177, 331811 5530176, 331817 5530175, 331823 5530173, 331828 5530171, 331832 5530169, 331835 5530167, 331839 5530163, 331843 5530160, 331846 5530157, 331849 5530154, 331853 5530150, 331855 5530145, 331857 5530141)")
      .test();
  }
  public void testFloatingPrecision2() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (317091 5557033, 317079 5557042, 317067 5557053, 317055 5557065, 317045 5557078, 317037 5557091, 317029 5557098, 317016 5557108, 317002 5557118, 316990 5557129, 316986 5557131, 316978 5557133, 316968 5557133, 316965 5557131, 316954 5557120, 316952 5557115, 316951 5557108, 316949 5557092, 316948 5557076, 316946 5557063, 316944 5557057, 316937 5557042, 316924 5557029, 316911 5557019, 316896 5557009, 316881 5557001, 316865 5556997, 316849 5556992, 316834 5556988, 316817 5556985, 316801 5556983, 316766 5556983, 316751 5556982, 316733 5556980, 316716 5556976, 316702 5556968, 316699 5556964, 316691 5556951, 316680 5556934, 316670 5556922, 316660 5556911, 316642 5556885, 316637 5556881)")
      .test();
  }
  public void testFloatingPrecision3() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (300181 5547255, 300183 5547252, 300203 5547253, 300209 5547261, 300237 5547277, 300262 5547286, 300280 5547292, 300288 5547297, 300293 5547303, 300297 5547311, 300299 5547319, 300299 5547334, 300306 5547349, 300320 5547367)")
      .test();
  }
  public void testFloatingPrecision4() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (301546 5537924, 301547 5537922, 301551 5537919, 301555 5537919, 301559 5537918, 301565 5537918, 301569 5537917, 301573 5537915, 301580 5537912, 301583 5537909, 301587 5537906, 301594 5537900, 301598 5537897, 301601 5537893, 301605 5537889, 301608 5537885, 301609 5537880, 301612 5537876, 301614 5537873, 301616 5537869, 301620 5537865, 301624 5537860, 301632 5537852, 301640 5537842, 301643 5537836, 301644 5537829, 301644 5537822, 301646 5537815, 301647 5537808, 301650 5537802, 301650 5537796, 301651 5537791, 301653 5537786, 301654 5537780, 301656 5537773, 301658 5537767, 301662 5537761)")
      .test();
  }
  public void testFloatingPrecision5() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (334797 5560136, 334781 5560129, 334777 5560128, 334762 5560122, 334760 5560121, 334752 5560116, 334745 5560109, 334742 5560103, 334741 5560098, 334736 5560087, 334731 5560082, 334726 5560081, 334708 5560072, 334691 5560063, 334674 5560052, 334660 5560048, 334655 5560048, 334633 5560049, 334621 5560046, 334616 5560043, 334596 5560034, 334586 5560025, 334573 5560009, 334562 5559982, 334549 5559943, 334543 5559923, 334538 5559905, 334535 5559887, 334530 5559869, 334536 5559853)")
      .test();
  }
  public void testFloatingPrecision6() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (316640 5563099, 316639 5563114, 316642 5563132, 316644 5563137, 316650 5563144, 316653 5563147, 316663 5563159, 316665 5563164, 316667 5563172, 316667 5563193, 316668 5563209, 316672 5563214, 316678 5563228, 316679 5563230, 316679 5563236, 316678 5563252, 316676 5563256, 316671 5563270, 316669 5563289, 316667 5563304, 316666 5563308, 316656 5563323, 316646 5563336, 316639 5563347)")
      .test();
  }
  public void testFloatingPrecision7() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (301178 5534835, 301189 5534837, 301218 5534837, 301229 5534836, 301237 5534836, 301245 5534838, 301268 5534838, 301273 5534837, 301279 5534838, 301286 5534838, 301289 5534839, 301296 5534842, 301302 5534844, 301306 5534846, 301309 5534850, 301313 5534853, 301316 5534856, 301319 5534868, 301320 5534873, 301323 5534877, 301326 5534882, 301334 5534896, 301340 5534902, 301344 5534908, 301348 5534913, 301352 5534919, 301357 5534925, 301363 5534932, 301369 5534937, 301375 5534941, 301382 5534949, 301386 5534955, 301397 5534964, 301402 5534967, 301407 5534972, 301411 5534975, 301414 5534980, 301418 5534986, 301419 5534989, 301422 5534994, 301426 5535000, 301438 5535012, 301444 5535017, 301456 5535030, 301457 5535033)")
      .test();
  }
  public void testFloatingPrecision8() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (303722 5533544, 303713 5533542, 303706 5533539, 303697 5533537, 303694 5533534, 303677 5533527, 303673 5533525, 303670 5533524, 303669 5533523, 303664 5533519, 303654 5533513, 303647 5533507, 303644 5533506, 303634 5533504, 303633 5533504, 303627 5533502)")
      .test();
  }
  public void testFloatingPrecision9() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (309969 5563538, 309955 5563542, 309941 5563548, 309913 5563562, 309896 5563569, 309883 5563576, 309868 5563586, 309855 5563594, 309841 5563603, 309830 5563614, 309818 5563624, 309805 5563635, 309791 5563645, 309778 5563654, 309766 5563663, 309752 5563672, 309722 5563692, 309709 5563699, 309681 5563713, 309667 5563721, 309651 5563728, 309631 5563734, 309615 5563739, 309602 5563747, 309589 5563756, 309578 5563766, 309566 5563775, 309554 5563785, 309542 5563796, 309538 5563801, 309535 5563810, 309532 5563828, 309533 5563833, 309540 5563855, 309546 5563868, 309552 5563884, 309556 5563900, 309559 5563916, 309561 5563933, 309561 5563970, 309559 5563988, 309554 5564003, 309550 5564018, 309546 5564032, 309542 5564047, 309538 5564061, 309531 5564074, 309528 5564077, 309518 5564090, 309507 5564101, 309493 5564110, 309492 5564111, 309480 5564119, 309474 5564121, 309458 5564123, 309443 5564125, 309426 5564125, 309408 5564123, 309393 5564123, 309377 5564126, 309373 5564129, 309364 5564139, 309360 5564147, 309359 5564151, 309359 5564155, 309362 5564159)")
      .test();
  }
  public void testFloatingPrecision10() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (299331 5536963, 299335 5536956, 299335 5536953, 299336 5536949, 299338 5536942, 299345 5536933, 299349 5536927, 299352 5536924, 299358 5536922, 299364 5536919, 299369 5536916, 299375 5536912, 299380 5536908, 299387 5536905, 299391 5536904, 299395 5536902, 299399 5536899, 299402 5536896, 299405 5536892, 299415 5536886, 299425 5536882, 299435 5536880, 299449 5536874, 299455 5536869, 299461 5536865, 299468 5536862, 299474 5536859, 299480 5536855, 299491 5536846, 299497 5536842, 299502 5536838, 299508 5536835, 299513 5536834, 299523 5536830, 299527 5536826, 299532 5536824, 299542 5536821, 299548 5536818, 299552 5536814, 299556 5536812, 299566 5536808, 299574 5536804, 299588 5536798, 299594 5536796, 299602 5536792, 299611 5536789, 299625 5536784, 299632 5536782, 299648 5536780, 299657 5536785, 299672 5536798, 299678 5536801, 299688 5536801, 299696 5536802, 299711 5536807, 299729 5536807, 299739 5536808, 299748 5536807, 299766 5536810, 299777 5536813, 299801 5536816, 299825 5536811, 299850 5536810, 299867 5536809, 299875 5536811, 299887 5536811, 299895 5536815, 299913 5536822)")
      .test();
  }
  public void testFloatingPrecision11() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (325089 5534737, 325089 5534733, 325093 5534723, 325099 5534718, 325118 5534712, 325129 5534709, 325147 5534709, 325164 5534732, 325164 5534740, 325162 5534746, 325159 5534749, 325144 5534760, 325143 5534763, 325145 5534782, 325162 5534800, 325184 5534812, 325187 5534815, 325190 5534831, 325196 5534852, 325205 5534867, 325214 5534876, 325219 5534878, 325239 5534880, 325251 5534890, 325259 5534892, 325282 5534887, 325294 5534883, 325314 5534864)")
      .test();
  }
  public void testFloatingPrecision12() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (307468 5557827, 307467 5557842, 307466 5557854, 307463 5557874, 307459 5557889, 307454 5557902, 307447 5557922, 307440 5557944, 307428 5557965, 307417 5557986, 307411 5557996, 307404 5558020, 307398 5558031, 307390 5558056, 307387 5558066, 307384 5558084, 307383 5558093, 307385 5558102, 307389 5558110, 307394 5558116, 307404 5558121, 307421 5558122, 307443 5558121, 307464 5558127, 307486 5558133, 307502 5558142, 307508 5558150)")
      .test();
  }
  public void testFloatingPrecision13() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (301395 5535820, 301412 5535803, 301416 5535798, 301420 5535786, 301423 5535782, 301427 5535778, 301432 5535771, 301437 5535768, 301444 5535763, 301447 5535760, 301452 5535757, 301459 5535754, 301462 5535753, 301468 5535750, 301473 5535747, 301481 5535742, 301487 5535739, 301494 5535734, 301499 5535730, 301508 5535725, 301514 5535721, 301521 5535716, 301527 5535714, 301533 5535711, 301538 5535707, 301542 5535703, 301554 5535693, 301559 5535688, 301563 5535681)")
      .test();
  }
  public void testFloatingPrecision14() throws Exception {
    new BufferValidator(
      100,
      "LINESTRING (331384 5535032, 331397 5535031, 331415 5535025, 331426 5535022, 331436 5535016, 331451 5534999, 331460 5534994, 331468 5534993, 331474 5534996, 331479 5535001, 331482 5535015, 331486 5535019, 331493 5535018, 331504 5535011, 331508 5535011, 331519 5535022, 331526 5535023, 331542 5535019, 331547 5535016, 331549 5534994, 331558 5534975, 331562 5534968, 331565 5534966, 331571 5534967, 331575 5534970, 331576 5534978, 331575 5534987, 331568 5535005, 331564 5535022, 331565 5535030, 331570 5535038, 331578 5535044, 331582 5535046, 331592 5535046, 331602 5535043, 331613 5535038, 331631 5535027, 331640 5535021, 331645 5535020, 331654 5535020, 331662 5535022, 331669 5535028, 331676 5535040, 331674 5535065, 331668 5535089, 331659 5535108, 331655 5535118, 331655 5535123, 331662 5535143, 331662 5535147, 331651 5535167, 331646 5535181, 331642 5535198, 331640 5535210, 331641 5535234, 331642 5535245, 331645 5535255, 331648 5535276, 331651 5535287, 331663 5535309, 331665 5535316, 331666 5535324, 331671 5535337, 331677 5535344)")
      .test();
  }

}
